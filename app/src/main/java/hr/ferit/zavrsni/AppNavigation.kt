package hr.ferit.zavrsni

sealed class AppNavigation (val route: String){
    data object Start : AppNavigation ("start_screen")
    data object RegisterScreen : AppNavigation("register_screen")
}