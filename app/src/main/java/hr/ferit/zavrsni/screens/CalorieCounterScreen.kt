package hr.ferit.zavrsni.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.components.ClicableTextComponent
import hr.ferit.zavrsni.components.DividerComponent
import hr.ferit.zavrsni.components.Footer
import hr.ferit.zavrsni.data.Breakfast
import hr.ferit.zavrsni.data.Dinner
import hr.ferit.zavrsni.data.Food
import hr.ferit.zavrsni.data.FoodViewModel
import hr.ferit.zavrsni.data.Lunch
import hr.ferit.zavrsni.data.ProfileDataUIState
import hr.ferit.zavrsni.data.ProfileDataViewModel
import hr.ferit.zavrsni.data.Snack
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.DarkGray
import hr.ferit.zavrsni.ui.theme.White
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun CalorieCounterScreen(navController: NavController,
                         foodViewModel: FoodViewModel = viewModel(),
                         profileDataViewModel: ProfileDataViewModel = viewModel(),
                         mealType: Int
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFoods by remember { mutableStateOf(listOf<Pair<Food, Int>>()) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedFood by remember { mutableStateOf<Food?>(null) }
    val allFoodItems by foodViewModel.allFoodItems.collectAsState()

    LaunchedEffect(Unit) {
        profileDataViewModel.loadDataFromFirestore(mealType)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(20.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp)),
            placeholder = { Text("Search food...") },
            textStyle = TextStyle(color = DarkGray),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
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

        if (searchQuery.isNotBlank()) {
            val searchResults = allFoodItems.filter { it.name.contains(searchQuery, ignoreCase = true) }
            LazyColumn {
                items(searchResults) { food ->
                    Text(
                        text = food.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedFood = food
                                showDialog = true
                            }
                            .padding(8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (mealType) {
            0 -> {
                Text("Breakfast Foods:")
                LazyColumn {
                    items(profileDataViewModel.profileData.value.breakfast.breakfastFoods) { food ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "${food.name}: ${food.grams}g (${food.calories}kcal/100g)",
                                modifier = Modifier.weight(1f)
                            )
                            Button(onClick = { profileDataViewModel.removeFoodFromMeal(mealType, food) }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
            1 -> {
                Text("Lunch Foods:")
                LazyColumn {
                    items(profileDataViewModel.profileData.value.lunch.lunchFoods) { food ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "${food.name}: ${food.grams}g (${food.calories}kcal/100g)",
                                modifier = Modifier.weight(1f)
                            )
                            Button(onClick = { profileDataViewModel.removeFoodFromMeal(mealType, food) }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
            2 -> {
                Text("Dinner Foods:")
                LazyColumn {
                    items(profileDataViewModel.profileData.value.dinner.dinnerFoods) { food ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "${food.name}: ${food.grams}g (${food.calories}kcal/100g)",
                                modifier = Modifier.weight(1f)
                            )
                            Button(onClick = { profileDataViewModel.removeFoodFromMeal(mealType, food) }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
            3 -> {
                Text("Snack Foods:")
                LazyColumn {
                    items(profileDataViewModel.profileData.value.snack.snackFoods) { food ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "${food.name}: ${food.grams}g (${food.calories}kcal/100g)",
                                modifier = Modifier.weight(1f)
                            )
                            Button(onClick = { profileDataViewModel.removeFoodFromMeal(mealType, food) }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val totalCalories = selectedFoods.sumOf { (food, grams) -> (food.calories * grams) / 100 }
        when (mealType) {

            0 -> {
                Text("Total Calories: ${profileDataViewModel.profileData.value.breakfast.breakfastCalories}")
            }

            1 -> {
                Text("Total Calories: ${profileDataViewModel.profileData.value.lunch.lunchCalories}")
            }

            2 -> {
                Text("Total Calories: ${profileDataViewModel.profileData.value.dinner.dinnerCalories}")
            }

            3 -> {
                Text("Total Calories: ${profileDataViewModel.profileData.value.snack.snackCalories}")
            }
            else -> Text("Total Calories: $totalCalories")
        }

        if (showDialog && selectedFood != null) {
            AddFoodDialog(
                food = selectedFood!!,
                onDismiss = { showDialog = false },
                onAdd = { grams ->
                    selectedFoods = selectedFoods + Pair(selectedFood!!, grams)
                    val totalCalories = selectedFoods.sumOf { (food, grams) -> (food.calories * grams) / 100 }

                    profileDataViewModel.viewModelScope.launch {
                        profileDataViewModel.saveMeal(mealType, selectedFood!!, grams)
                    }
                    showDialog = false
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        DividerComponent()

        Spacer(modifier = Modifier.height(20.dp))

        ClicableTextComponent(tryinToAddProduct = false, onTextSelected = {
            navController.navigate(route = AppNavigation.FoodEntryScreen.route)
        })

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Footer(navController)
        }
    }
}

@Composable
fun AddFoodDialog(food: Food, onDismiss: () -> Unit, onAdd: (Int) -> Unit) {
    var grams by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add ${food.name}") },
        text = {
            Column {
                Text("Enter the amount in grams:")
                TextField(
                    value = grams,
                    onValueChange = { grams = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val gramsInt = grams.toIntOrNull() ?: 0
                if (gramsInt > 0) {
                    onAdd(gramsInt)
                }
                onDismiss()
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

suspend fun getDataFromFirestore(uid: String, mealType: Int): ProfileDataUIState {
    val firestore = FirebaseFirestore.getInstance()
    val docRef = firestore.collection("profileData").document(uid)

    val documentSnapshot = docRef.get().await()

    if (documentSnapshot.exists()) {
        val data = documentSnapshot.toObject(ProfileDataUIState::class.java)
        return when (mealType) {
            0 -> ProfileDataUIState(breakfast = data?.breakfast ?: Breakfast())
            1 -> ProfileDataUIState(lunch = data?.lunch ?: Lunch())
            2 -> ProfileDataUIState(dinner = data?.dinner ?: Dinner())
            3 -> ProfileDataUIState(snack = data?.snack ?: Snack())
            else -> ProfileDataUIState()
        }
    } else {
        return ProfileDataUIState()
    }
}



