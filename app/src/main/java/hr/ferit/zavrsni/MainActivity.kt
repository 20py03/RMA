package hr.ferit.zavrsni


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.zavrsni.data.EnergyDataUIState
import hr.ferit.zavrsni.data.MealDataUIState
import hr.ferit.zavrsni.data.MealDataViewModel
import hr.ferit.zavrsni.data.ProfileDataUIState
import hr.ferit.zavrsni.data.ProfileDataViewModel
import hr.ferit.zavrsni.ui.theme.ZavrsniTheme
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {
    object WaterFoodNotification {
        private const val CHANNEL_ID = "water_and_calories_channel"

        fun showNotification(context: Context, title: String, content: String, notificationId: Int) {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.gym_dumbbell_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                notify(notificationId, builder.build())
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZavrsniTheme {
                AppNavGraph()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            } else {
                checkAndShowNotifications()
            }
        } else {
            checkAndShowNotifications()
        }
    }

    private fun checkAndShowNotifications() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Log.d("MainActivity", "User not logged in, skipping notifications.")
            return
        }

        val db = FirebaseFirestore.getInstance()
        val uid = currentUser.uid
        db.collection("profileData").document(uid).get()
            .addOnSuccessListener { document ->
                try {
                    val data = document.toObject(MealDataUIState::class.java)
                    if (data == null) {
                        Log.e("MainActivity", "MealDataUIState is null")
                        return@addOnSuccessListener
                    }

                    val waterIntake = data.waterGlasses.count { it }
                    val goalWaterIntake = 7
                    val totalCalories = data.breakfast.breakfastCalories +
                            data.lunch.lunchCalories +
                            data.dinner.dinnerCalories +
                            data.snack.snackCalories
                    val goalCalories = document.toObject(EnergyDataUIState::class.java)!!.energyData.goalCalories.toIntOrNull() ?: 0


                    if (waterIntake != goalWaterIntake) {
                        WaterFoodNotification.showNotification(this, "Hydration Alert", "You haven't drunk enough water today!", 1)
                    }
                    if (totalCalories > goalCalories) {
                        WaterFoodNotification.showNotification(this, "Calorie Alert", "You've exceeded your calorie intake goal!", 2)
                    } else if (totalCalories < goalCalories) {
                        WaterFoodNotification.showNotification(this, "Calorie Alert", "You haven't met your calorie intake goal!", 3)
                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error processing Firestore document", e)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Error getting documents: ", exception)
            }
    }
}















