package hr.ferit.zavrsni.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EnergyDataViewModel : ViewModel() {
    val state = mutableStateOf(EnergyDataUIState())

    fun calculateEnergyData(
        gender: String,
        age: String,
        height: String,
        weight: String,
        activity: String,
        goal: String
    ) {
        viewModelScope.launch {
            val tdee = calculateTDEE(gender, age, height, weight, activity)
            val goalCalories = calculateGoalCalories(tdee, goal)
            val protein = calculateProtein(weight)
            val fat = calculateFat(goalCalories)
            val carbohydrates = calculateCarbohydrates(goalCalories, protein, fat)

            state.value = EnergyDataUIState(
                EnergyData(
                    tdee = formatValue(tdee),
                    goalCalories = formatValue(goalCalories),
                    protein = formatValue(protein),
                    fat = formatValue(fat),
                    carbohydrates = formatValue(carbohydrates)
                )
            )
        }
    }

    private fun calculateTDEE(gender: String, age: String, height: String, weight: String, activity: String): Double {
        val ageInt = age.toIntOrNull() ?: 0
        val heightDouble = height.toDoubleOrNull() ?: 0.0
        val weightDouble = weight.toDoubleOrNull() ?: 0.0

        val bmr = if (gender.lowercase() == "male") {
            10 * weightDouble + 6.25 * heightDouble - 5 * ageInt + 5
        } else {
            10 * weightDouble + 6.25 * heightDouble - 5 * ageInt - 161
        }

        val activityFactor = when (activity.lowercase()) {
            "sedentary" -> 1.2
            "lightly active" -> 1.375
            "moderately active" -> 1.55
            "very active" -> 1.725
            "athlete" -> 1.9
            else -> 1.2
        }

        return bmr * activityFactor
    }

    private fun calculateGoalCalories(tdee: Double, goal: String): Double {
        return when (goal.lowercase()) {
            "lose weight" -> tdee - 300
            "build muscle" -> tdee + 300
            "get leaner" -> tdee - 500
            "flexibility" -> tdee
            "improve health " -> tdee - 100
            else -> tdee
        }
    }

    private fun calculateProtein(weight: String): Double {
        val weightDouble = weight.toDoubleOrNull() ?: 0.0
        return weightDouble * 2.2
    }

    private fun calculateFat(goalCalories: Double): Double {
        return goalCalories * 0.25 / 9
    }

    private fun calculateCarbohydrates(goalCalories: Double, protein: Double, fat: Double): Double {
        val proteinCalories = protein * 4
        val fatCalories = fat * 9
        return (goalCalories - proteinCalories - fatCalories) / 4
    }

    private fun formatValue(value: Double): String {
        return String.format("%.0f", value)
    }

}
