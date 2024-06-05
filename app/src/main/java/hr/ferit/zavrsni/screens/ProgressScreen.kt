package hr.ferit.zavrsni.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.components.EmotionLabel
import hr.ferit.zavrsni.components.Footer
import hr.ferit.zavrsni.data.LoginViewModel
import hr.ferit.zavrsni.data.ProfileDataViewModel
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.DarkGray
import hr.ferit.zavrsni.ui.theme.LightGray
import hr.ferit.zavrsni.ui.theme.White
import androidx.compose.runtime.collectAsState
import hr.ferit.zavrsni.data.ProgressDataViewModel

@Composable
fun ProgressScreen(navController: NavController,
                   loginViewModel: LoginViewModel = viewModel(),
                   progressDataViewModel: ProgressDataViewModel = viewModel()
) {
    val context = LocalContext.current
    val state by progressDataViewModel.state

    var beforeWeight by rememberSaveable { mutableStateOf(state.beforeWeight) }
    var afterWeight by rememberSaveable { mutableStateOf(state.afterWeight) }
    var currentWeight by rememberSaveable { mutableStateOf(state.currentWeight) }
    var note1 by rememberSaveable { mutableStateOf(state.note1) }
    var note2 by rememberSaveable { mutableStateOf(state.note2) }

    LaunchedEffect(Unit) {
        progressDataViewModel.getProgressData()
    }

    LaunchedEffect(state) {
        beforeWeight = state.beforeWeight
        currentWeight = state.currentWeight
        afterWeight = state.afterWeight
        note1 = state.note1
        note2 = state.note2
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "Start weight", color = DarkGray, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text(text = "Current weight", color = DarkGray, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text(text = "Goal weight", color = DarkGray, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextField(
                    value = beforeWeight,
                    onValueChange = { beforeWeight = it },
                    modifier = Modifier
                        .size(80.dp)
                        .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .padding(bottom = 10.dp)
                        .border(2.dp, Blue, shape = RoundedCornerShape(8.dp)),
                    textStyle = TextStyle(color = Color.Black),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = Blue,
                        cursorColor = DarkGray,
                        focusedTextColor = Blue,
                        unfocusedTextColor = Blue,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    )
                )

                TextField(
                    value = currentWeight,
                    onValueChange = { currentWeight = it },
                    modifier = Modifier
                        .size(80.dp)
                        .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .padding(bottom = 10.dp)
                        .border(2.dp, Blue, shape = RoundedCornerShape(8.dp)),
                    textStyle = TextStyle(color = Color.Black),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = Blue,
                        cursorColor = DarkGray,
                        focusedTextColor = Blue,
                        unfocusedTextColor = Blue,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    )
                )

                TextField(
                    value = afterWeight,
                    onValueChange = { afterWeight = it },
                    modifier = Modifier
                        .size(80.dp)
                        .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .padding(bottom = 10.dp)
                        .border(2.dp, Blue, shape = RoundedCornerShape(8.dp)),
                    textStyle = TextStyle(color = Color.Black),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = Blue,
                        cursorColor = DarkGray,
                        focusedTextColor = Blue,
                        unfocusedTextColor = Blue,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    )
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            EmotionLabel("How are you feeling today?")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = note1,
                onValueChange = { note1 = it },
                modifier = Modifier
                    .height(175.dp)
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .border(2.dp, Blue, shape = RoundedCornerShape(8.dp)),
                textStyle = TextStyle(color = Blue),
                maxLines = 10,
                placeholder = { Text(text = "Write your thoughts...") },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Blue,
                    cursorColor = DarkGray,
                    focusedTextColor = Blue,
                    unfocusedTextColor = Blue,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White
                )
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            EmotionLabel("What can you improve?")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = note2,
                onValueChange = { note2 = it },
                modifier = Modifier
                    .height(175.dp)
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .border(2.dp, Blue, shape = RoundedCornerShape(8.dp)),
                textStyle = TextStyle(color = Blue),
                maxLines = 10,
                placeholder = { Text(text = "Write your thoughts...") },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Blue,
                    cursorColor = DarkGray,
                    focusedTextColor = Blue,
                    unfocusedTextColor = Blue,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Button(
                onClick = {
                    if (loginViewModel.currentUser != null) {
                        val uid = loginViewModel.currentUser?.uid
                        if(uid != null){
                            progressDataViewModel.saveNotes(note1, note2)
                            progressDataViewModel.saveWeights(beforeWeight, currentWeight, afterWeight)
                            Toast.makeText(
                                context,
                                "Data saved successfully",
                                Toast.LENGTH_SHORT
                            ).show()
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
                    .padding(top = 10.dp, bottom = 10.dp)
                    .height(50.dp)
                    .fillMaxWidth(0.7f)
                    .clip(RoundedCornerShape(8.dp)),
            ) {
                Text(
                    text = "Save data",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
        Footer(navController)
    }
}