package hr.ferit.zavrsni.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.data.LoginViewModel
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.DarkBlue
import hr.ferit.zavrsni.ui.theme.LightPink
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun GoalScreen(navController: NavController, loginViewModel : LoginViewModel = viewModel()) {

    val context = LocalContext.current
    var selectedGoal by remember { mutableStateOf("") }

    val goals = listOf(
        "Lose weight",
        "Build muscle",
        "Get leaner",
        "Flexibility",
        "Improve health"
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
            text = "What is your goal?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Blue,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 25.dp)
        )

        goals.forEach { goal ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { selectedGoal = goal })
                    .padding(8.dp)
                    .background(
                        color = if (goal == selectedGoal) LightPink else Blue,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = goal,
                    fontSize = 18.sp,
                    color = if (goal == selectedGoal) Blue else DarkBlue,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Button(
                onClick = {
                    if (loginViewModel.currentUser != null) {
                        val uid = loginViewModel.currentUser?.uid
                        if(uid!=null){
                            saveGoalToFirestore(selectedGoal, uid, navController)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Please log in to continue",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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

private fun saveGoalToFirestore(goal: String, uid:String, navController:NavController) {
    val db = FirebaseFirestore.getInstance()
    val profileData = hashMapOf(
        "goal" to goal
    )

    db.collection("profileData").document(uid)
        .set(profileData, SetOptions.merge())
        .addOnSuccessListener {
            navController.navigate(route =  AppNavigation.HomeScreen.route)
        }
        .addOnFailureListener { e ->
            Log.d("error","Inside_OnFailureListener: $e")
        }
}
