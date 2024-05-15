package hr.ferit.zavrsni

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hr.ferit.zavrsni.screens.RegisterScreen
import hr.ferit.zavrsni.screens.Start

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = AppNavigation.Start.route
    ){
        composable(route = AppNavigation.Start.route){
            Start(navController = navController)
        }
        composable(route=AppNavigation.RegisterScreen.route){
            RegisterScreen()
        }
    }
}