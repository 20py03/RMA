package hr.ferit.zavrsni

sealed class AppNavigation (val route: String){
    data object StartScreen : AppNavigation ("start_screen")
    data object RegisterScreen : AppNavigation("register_screen")
    data object LoginScreen : AppNavigation("login_screen")
    data object GenderScreen : AppNavigation("gender_screen")
    data object YearScreen : AppNavigation("year_screen")
    data object WeightScreen : AppNavigation("weight_screen")
    data object HeightScreen : AppNavigation("height_screen")
    data object ActivityScreen : AppNavigation("activity_screen")
    data object GoalScreen : AppNavigation("goal_screen")
    data object HomeScreen : AppNavigation("home_screen")
    data object RecipeScreen : AppNavigation("recipe_screen")
    data object ProfileScreen : AppNavigation("profile_screen")

}