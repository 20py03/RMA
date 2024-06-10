package hr.ferit.zavrsni.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.R
import hr.ferit.zavrsni.components.Footer
import hr.ferit.zavrsni.components.MealSquare
import hr.ferit.zavrsni.data.MealDataViewModel
import hr.ferit.zavrsni.data.ProfileDataViewModel
import hr.ferit.zavrsni.data.SharedViewModel
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.DarkBlue
import hr.ferit.zavrsni.ui.theme.DarkGray
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun HomeScreen(
    navController: NavController,
    profileDataViewModel: ProfileDataViewModel = viewModel(),
    mealDataViewModel: MealDataViewModel = viewModel(),
    sharedViewModel: SharedViewModel
) {

    val energyData = profileDataViewModel.energyDataViewModel.state.value
    val goalCalories: Int = energyData.goalCalories.toIntOrNull() ?: 0
    val breakfastCalories: Int = mealDataViewModel.state.value.breakfast.breakfastCalories
    val lunchCalories: Int = mealDataViewModel.state.value.lunch.lunchCalories
    val dinnerCalories: Int = mealDataViewModel.state.value.dinner.dinnerCalories
    val snackCalories: Int = mealDataViewModel.state.value.snack.snackCalories

    val eatenCal: Int = breakfastCalories + lunchCalories + dinnerCalories + snackCalories
    val remainingCal: Int = goalCalories - eatenCal

    LaunchedEffect(Unit) {
        mealDataViewModel.getMealData()
        profileDataViewModel.getProfileData()
    }

    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(color = White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(340.dp)
                    .fillMaxSize()
                    .padding(10.dp)
                    .background(color = Blue, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 30.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "$eatenCal \nkcal eaten",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "${energyData.goalCalories}\nkcal",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "$remainingCal \nkcal left",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Carbs\n ${energyData.carbohydrates}g",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Protein\n ${energyData.protein}g",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Fat\n ${energyData.fat}g",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        mealDataViewModel.state.value.waterGlasses.forEachIndexed { index, isClicked ->
                            IconButton(onClick = {
                                mealDataViewModel.toggleWaterGlass(index)
                            }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.water_glass_color_icon),
                                    contentDescription = "WaterGlass",
                                    tint = if (isClicked) DarkBlue else DarkGray
                                )
                            }
                        }
                    }

                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MealSquare(label = "Breakfast\n" + "\n${mealDataViewModel.state.value.breakfast.breakfastCalories} kcal") {
                    sharedViewModel.mealType.value = 0
                    navController.navigate(route = AppNavigation.CalorieCounterScreen.route)
                }
                MealSquare(label = "Lunch\n" + "\n${mealDataViewModel.state.value.lunch.lunchCalories} kcal") {
                    sharedViewModel.mealType.value = 1
                    navController.navigate(route = AppNavigation.CalorieCounterScreen.route)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MealSquare(label = "Dinner\n" + "\n${mealDataViewModel.state.value.dinner.dinnerCalories} kcal") {
                    sharedViewModel.mealType.value = 2
                    navController.navigate(route = AppNavigation.CalorieCounterScreen.route)
                }
                MealSquare(label = "Snack\n"+ "\n${mealDataViewModel.state.value.snack.snackCalories} kcal") {
                    sharedViewModel.mealType.value = 3
                    navController.navigate(route = AppNavigation.CalorieCounterScreen.route)
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


