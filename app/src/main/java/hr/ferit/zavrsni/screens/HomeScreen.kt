package hr.ferit.zavrsni.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.R
import hr.ferit.zavrsni.components.Footer
import hr.ferit.zavrsni.components.MealSquare
import hr.ferit.zavrsni.data.Food
import hr.ferit.zavrsni.data.ProfileDataViewModel
import hr.ferit.zavrsni.data.SharedViewModel
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.DarkBlue
import hr.ferit.zavrsni.ui.theme.LightPink
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun HomeScreen(navController: NavController,
               profileDataViewModel: ProfileDataViewModel = viewModel(),
               sharedViewModel: SharedViewModel,
) {

    val getData = profileDataViewModel.state.value
    val energyData = profileDataViewModel.energyDataViewModel.state.value

    LaunchedEffect(Unit) {
        profileDataViewModel.getData()
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
                            text = "1800 \nkcal eaten",
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
                            text = "200 \nkcal left",
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

                    val (iconColors, setIconColors) = remember {
                        mutableStateOf(List(7) { LightPink })
                    }

                    fun toggleIconColor(index: Int) {
                        val newColors = iconColors.toMutableList()
                        newColors[index] =
                            if (iconColors[index] == LightPink) DarkBlue else LightPink
                        setIconColors(newColors)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(7) { index ->
                            IconButton(onClick = { toggleIconColor(index) }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.water_glass_color_icon),
                                    contentDescription = "WaterGlass",
                                    modifier = Modifier,
                                    tint = iconColors[index]
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
                MealSquare(label = "Breakfast\n" + "\n${getData.breakfast.breakfastCalories} kcal") {
                    sharedViewModel.mealType.value = 0
                    navController.navigate(route = AppNavigation.CalorieCounterScreen.route)
                }
                MealSquare(label = "Lunch\n" + "\n${getData.lunch.lunchCalories} kcal") {
                    sharedViewModel.mealType.value = 1
                    navController.navigate(route = AppNavigation.CalorieCounterScreen.route)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MealSquare(label = "Dinner\n" + "\n${getData.dinner.dinnerCalories} kcal") {
                    sharedViewModel.mealType.value = 2
                    navController.navigate(route = AppNavigation.CalorieCounterScreen.route)
                }
                MealSquare(label = "Snack\n"+ "\n${getData.snack.snackCalories} kcal") {
                    sharedViewModel.mealType.value = 3
                    navController.navigate(route = AppNavigation.CalorieCounterScreen.route)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Footer(navController)
            }
        }
    }
}


