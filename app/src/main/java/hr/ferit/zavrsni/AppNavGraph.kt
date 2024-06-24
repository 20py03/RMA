package hr.ferit.zavrsni

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hr.ferit.zavrsni.data.LoginViewModel
import hr.ferit.zavrsni.data.SharedViewModel
import hr.ferit.zavrsni.screens.ActivityScreen
import hr.ferit.zavrsni.screens.CalorieCounterScreen
import hr.ferit.zavrsni.screens.EditProfileScreen
import hr.ferit.zavrsni.screens.FoodEntryScreen
import hr.ferit.zavrsni.screens.GenderScreen
import hr.ferit.zavrsni.screens.GoalScreen
import hr.ferit.zavrsni.screens.HeightScreen
import hr.ferit.zavrsni.screens.HomeScreen
import hr.ferit.zavrsni.screens.LoginScreen
import hr.ferit.zavrsni.screens.ProfileScreen
import hr.ferit.zavrsni.screens.ProgressScreen
import hr.ferit.zavrsni.screens.RecipeScreen
import hr.ferit.zavrsni.screens.RegisterScreen
import hr.ferit.zavrsni.screens.StartScreen
import hr.ferit.zavrsni.screens.WeightScreen
import hr.ferit.zavrsni.screens.YearScreen

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController(),
                loginViewModel: LoginViewModel = viewModel(),
                sharedViewModel: SharedViewModel = viewModel()){
    NavHost(
        navController = navController,
        startDestination = if(loginViewModel.currentUser != null ){
             AppNavigation.HomeScreen.route
        }else{
            AppNavigation.StartScreen.route
        }
    ){
        composable(route = AppNavigation.StartScreen.route){
            StartScreen(navController = navController)
        }
        composable(route=AppNavigation.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }
        composable(route = AppNavigation.LoginScreen.route){
            LoginScreen(navController = navController)
        }
        composable(route = AppNavigation.GenderScreen.route){
            GenderScreen(navController = navController)
        }
        composable(route = AppNavigation.YearScreen.route){
            YearScreen(navController = navController)
        }
        composable(route = AppNavigation.WeightScreen.route){
            WeightScreen(navController = navController)
        }
        composable(route = AppNavigation.HeightScreen.route){
            HeightScreen(navController = navController)
        }
        composable(route = AppNavigation.ActivityScreen.route){
            ActivityScreen(navController = navController)
        }
        composable(route = AppNavigation.GoalScreen.route){
            GoalScreen(navController = navController)
        }
        composable(route = AppNavigation.HomeScreen.route){
            HomeScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = AppNavigation.ProgressScreen.route){
            ProgressScreen(navController = navController)
        }
        composable(route = AppNavigation.RecipeScreen.route){
            RecipeScreen(navController = navController)
        }
        composable(route = AppNavigation.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }
        composable(route = AppNavigation.FoodEntryScreen.route){
            FoodEntryScreen(navController = navController)
        }
        composable(route = AppNavigation.CalorieCounterScreen.route){
            CalorieCounterScreen(navController = navController, mealType = sharedViewModel.mealType.value)
        }
        composable(route = AppNavigation.EditProfileScreen.route){
            EditProfileScreen(navController = navController)
        }
    }
}