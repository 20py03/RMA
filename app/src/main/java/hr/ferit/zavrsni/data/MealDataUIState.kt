package hr.ferit.zavrsni.data

data class MealDataUIState (
    var breakfast: Breakfast = Breakfast(),
    var lunch: Lunch = Lunch(),
    var dinner: Dinner = Dinner(),
    var snack: Snack = Snack(),
    val waterGlasses: List<Boolean> = List(7) { false }
)

data class Breakfast(
    var breakfastFoods: List<Food> = emptyList(),
    val breakfastCalories: Int = 0,
)

data class Lunch(
    var lunchFoods: List<Food> = emptyList(),
    val lunchCalories: Int = 0,
)

data class Dinner(
    var dinnerFoods: List<Food> = emptyList(),
    val dinnerCalories: Int = 0,
)

data class Snack(
    var snackFoods: List<Food> = emptyList(),
    val snackCalories: Int = 0,
)