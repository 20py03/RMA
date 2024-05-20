package hr.ferit.zavrsni.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.LightPink
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun ActivityScreen(navController: NavController) {
    var selectedActivity by remember { mutableStateOf("") }

    val activityLevels = listOf(
        "Sedentary",
        "Lightly Active",
        "Moderately Active",
        "Very Active",
        "Athlete"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "How active are you?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Blue,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 25.dp)
        )

        activityLevels.forEach { level ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { selectedActivity = level })
                    .padding(8.dp)
                    .background(
                        color = if (level == selectedActivity) LightPink else Blue,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = level,
                    fontSize = 18.sp,
                    color = if (level == selectedActivity) Blue else LightPink,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Button(
            onClick = {
                saveActivityToFirestore(selectedActivity, navController)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            modifier = Modifier
                .padding(top = 60.dp)
                .height(56.dp)
                .fillMaxWidth(0.7f)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            Text(
                text = "Next",
                color = White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

private fun saveActivityToFirestore(activity: String, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val profileData = hashMapOf(
        "activity" to activity
    )
    val documentId = "sGpBjAIYif34nHvNX0gB"

    db.collection("profileData").document(documentId)
        .set(profileData, SetOptions.merge())
        .addOnSuccessListener {
            navController.navigate(route =  AppNavigation.GoalScreen.route)
        }
        .addOnFailureListener { e ->
            Log.d("error","Inside_OnFailureListener: $e")
        }
}
