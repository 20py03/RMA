package hr.ferit.zavrsni.data

data class ProfileDataUIState (
    val name: String="",
    val gender: String="",
    val age:String="",
    val weight:String="",
    val height:String="",
    val activity:String="",
    val goal:String="",

    val beforeWeight: String = "",
    val currentWeight: String = "",
    val afterWeight: String = "",
    val note1: String = "",
    val note2: String = "",

    val breakfastCalories: Int = 0,
    val lunchCalories: Int = 0,
    val dinnerCalories: Int = 0,
    val snackCalories: Int = 0,

    val breakfastFoods: List<Food> = emptyList(),
    val lunchFoods: List<Food> = emptyList(),
    val dinnerFoods: List<Food> = emptyList(),
    val snackFoods: List<Food> = emptyList(),

)
