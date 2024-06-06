package hr.ferit.zavrsni.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.components.Footer
import hr.ferit.zavrsni.data.Food
import hr.ferit.zavrsni.data.FoodViewModel
import hr.ferit.zavrsni.data.LoginViewModel
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.DarkGray
import hr.ferit.zavrsni.ui.theme.White
import java.util.UUID


@Composable
fun FoodEntryScreen(navController: NavController,
                    foodViewModel: FoodViewModel = viewModel(),
                    loginViewModel: LoginViewModel = viewModel ()
) {

    val context = LocalContext.current
    var foodName by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fats by remember { mutableStateOf("") }

    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add food below!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Blue,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            OutlinedTextField(
                value = foodName,
                label = { Text(text = "Name") },
                onValueChange = { foodName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .padding(15.dp),
                textStyle = TextStyle(color = DarkGray),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
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

            OutlinedTextField(
                value = calories,
                label = { Text(text = "Calories/100g") },
                onValueChange = { calories = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .padding(15.dp),
                textStyle = TextStyle(color = DarkGray),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Next),
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

            OutlinedTextField(
                value = protein,
                label = { Text(text = "Protein/100g") },
                onValueChange = { protein = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .padding(15.dp),
                textStyle = TextStyle(color = DarkGray),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Next),
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

            OutlinedTextField(
                value = carbs,
                label = { Text(text = "Carbs/100g") },
                onValueChange = { carbs = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .padding(15.dp),
                textStyle = TextStyle(color = DarkGray),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Next),
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

            OutlinedTextField(
                value = fats,
                label = { Text(text = "Fat/100g") },
                onValueChange = { fats = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .padding(15.dp),
                textStyle = TextStyle(color = DarkGray),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Done),
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Button(
                    onClick = {
                        if (loginViewModel.currentUser != null) {
                            val uid = loginViewModel.currentUser?.uid
                            if(uid != null){
                                val foodId = UUID.randomUUID().toString()
                                val food = Food(
                                    foodId,
                                    foodName,
                                    calories.toInt(),
                                    protein.toInt(),
                                    carbs.toInt(),
                                    fats.toInt()
                                )
                                foodViewModel.addFood(food)
                                Toast.makeText(
                                    context,
                                    "Data saved successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate(route = AppNavigation.CalorieCounterScreen.route)
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
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Footer(navController)
    }
}