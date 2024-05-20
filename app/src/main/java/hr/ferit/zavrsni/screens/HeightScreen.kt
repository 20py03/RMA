package hr.ferit.zavrsni.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun HeightScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "How tall are you?",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 70.dp, bottom = 150.dp)
        )

        Box(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(0.3f)
                .height(10.dp)
                .background(color = Blue)
        )

        var selectedHeight by remember { mutableStateOf(170) }  // Default height value

        val heights = (150..200).toList()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 25.dp)
            ) {
                items(heights.size) { index ->
                    val height = heights[index]
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 33.dp)
                            .clickable { selectedHeight = height },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = height.toString(),
                            fontSize = 20.sp,
                            color = if (height == selectedHeight) Blue else Color.Black
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .padding(vertical = 16.dp)
                .height(10.dp)
                .background(color = Blue)
        )

        Button(
            onClick = {
                saveHeightToFirestore(selectedHeight, navController)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            modifier = Modifier
                .padding(top = 130.dp)
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

private fun saveHeightToFirestore(height: Int, navController:NavController) {
    val db = FirebaseFirestore.getInstance()
    val profileData = hashMapOf(
        "height" to height.toString()
    )
    val documentId = "sGpBjAIYif34nHvNX0gB"

    db.collection("profileData").document(documentId)
        .set(profileData, SetOptions.merge())
        .addOnSuccessListener {
            navController.navigate(route =  AppNavigation.ActivityScreen.route)
        }
        .addOnFailureListener { e ->
            Log.d("error","Inside_OnFailureListener: $e")
        }
}
