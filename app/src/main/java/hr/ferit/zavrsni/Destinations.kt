package hr.ferit.zavrsni

sealed class Destinations (val route: String){
    data object StartScreen: Destinations("StartScreen")
    data object RegisterScreen: Destinations("RegisterScreen")
    data object GenderScreen: Destinations("GenderScreen")
    data object YearScreen: Destinations("YearScreen")
    data object HeightScreen: Destinations("HeightScreen")
    data object WeightScreen: Destinations("WeightScreen")
    data object ActivityScreen: Destinations("ActivityScreen")
    data object GoalScreen: Destinations("GoalScreen")
    data object HomeScreen : Destinations("HomeScreen")
    data object RecipeScreen: Destinations("RecipeScreen")


}