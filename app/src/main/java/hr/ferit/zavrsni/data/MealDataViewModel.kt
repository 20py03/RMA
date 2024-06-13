package hr.ferit.zavrsni.data

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MealDataViewModel : ViewModel() {
    val state = mutableStateOf(MealDataUIState())

    init {
        getMealData()
    }

    private fun getCurrentUserUid(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    private val _profileData = MutableStateFlow(MealDataUIState())
    val profileData: StateFlow<MealDataUIState> = _profileData

    fun getMealData() {
        viewModelScope.launch {
            val uid = getCurrentUserUid()
            if (uid != null) {
                val data = getDataFromFirestore(uid)
                state.value = data
                _profileData.value = data
            }
        }
    }

    private suspend fun updateFoodDataInFirestore(uid: String, updates: Map<String, Any>) {
        val db = FirebaseFirestore.getInstance()

        try {
            db.collection("profileData").document(uid).update(updates).await()
            Log.d("success", "Profile data updated successfully.")
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "updateProfileDataInFirestore: $e")
        }
        getMealData()
    }

    fun removeFoodFromMeal(mealType: Int, food: Food) {
        viewModelScope.launch {
            val uid = getCurrentUserUid()
            if (uid != null) {
                when (mealType) {
                    0 -> {
                        val newFoods = _profileData.value.breakfast.breakfastFoods.filter { it != food }
                        val updatedCalories = _profileData.value.breakfast.breakfastCalories - (food.calories * food.grams) / 100
                        _profileData.value = _profileData.value.copy(
                            breakfast = _profileData.value.breakfast.copy(
                                breakfastFoods = newFoods,
                                breakfastCalories = updatedCalories
                            )
                        )
                    }
                    1 -> {
                        val newFoods = _profileData.value.lunch.lunchFoods.filter { it != food }
                        val updatedCalories = _profileData.value.lunch.lunchCalories - (food.calories * food.grams) / 100
                        _profileData.value = _profileData.value.copy(
                            lunch = _profileData.value.lunch.copy(
                                lunchFoods = newFoods,
                                lunchCalories = updatedCalories
                            )
                        )
                    }
                    2 -> {
                        val newFoods = _profileData.value.dinner.dinnerFoods.filter { it != food }
                        val updatedCalories = _profileData.value.dinner.dinnerCalories - (food.calories * food.grams) / 100
                        _profileData.value = _profileData.value.copy(
                            dinner = _profileData.value.dinner.copy(
                                dinnerFoods = newFoods,
                                dinnerCalories = updatedCalories
                            )
                        )
                    }
                    3 -> {
                        val newFoods = _profileData.value.snack.snackFoods.filter { it != food }
                        val updatedCalories = _profileData.value.snack.snackCalories - (food.calories * food.grams) / 100
                        _profileData.value = _profileData.value.copy(
                            snack = _profileData.value.snack.copy(
                                snackFoods = newFoods,
                                snackCalories = updatedCalories,
                            )
                        )
                    }
                }

                val updates = when (mealType) {
                    0 -> mapOf("breakfast" to _profileData.value.breakfast)
                    1 -> mapOf("lunch" to _profileData.value.lunch)
                    2 -> mapOf("dinner" to _profileData.value.dinner)
                    3 -> mapOf("snack" to _profileData.value.snack)
                    else -> emptyMap()
                }
                updateFoodDataInFirestore(uid, updates)
            }
        }
    }

    suspend fun getDataFromFirestore(uid: String): MealDataUIState {
        val firestore = FirebaseFirestore.getInstance()
        val docRef = firestore.collection("profileData").document(uid)

        val documentSnapshot = docRef.get().await()

        if (documentSnapshot.exists()) {
            val data = documentSnapshot.toObject(MealDataUIState::class.java)!!
            return MealDataUIState(
                breakfast = data.breakfast,
                lunch = data.lunch,
                dinner = data.dinner,
                snack = data.snack,
                waterGlasses = data.waterGlasses
            )
        } else {
            return MealDataUIState()
        }
    }

    fun saveMeal(mealType: Int, food: Food, grams: Int) {
        val uid = getCurrentUserUid() ?: return
        val firestore = FirebaseFirestore.getInstance()
        val docRef = firestore.collection("profileData").document(uid)

        val caloriesForFood = (food.calories * grams) / 100 // Calculate calories for the specific food being added

        try {
            when (mealType) {
                0 -> {
                    val updatedBreakfastFoods = _profileData.value.breakfast.breakfastFoods + food.copy(grams = grams)
                    val updatedCalories = _profileData.value.breakfast.breakfastCalories + caloriesForFood
                    _profileData.value.breakfast = Breakfast(updatedBreakfastFoods, updatedCalories)
                    docRef.update("breakfast", _profileData.value.breakfast)
                }

                1 -> {
                    val updatedLunchFoods = _profileData.value.lunch.lunchFoods + food.copy(grams = grams)
                    val updatedCalories = _profileData.value.lunch.lunchCalories + caloriesForFood
                    _profileData.value.lunch = Lunch(updatedLunchFoods, updatedCalories)
                    docRef.update("lunch", _profileData.value.lunch)
                }

                2 -> {
                    val updatedDinnerFoods = _profileData.value.dinner.dinnerFoods + food.copy(grams = grams)
                    val updatedCalories = _profileData.value.dinner.dinnerCalories + caloriesForFood
                    _profileData.value.dinner = Dinner(updatedDinnerFoods, updatedCalories)
                    docRef.update("dinner", _profileData.value.dinner)
                }

                3 -> {
                    val updatedSnackFoods = _profileData.value.snack.snackFoods + food.copy(grams = grams)
                    val updatedCalories = _profileData.value.snack.snackCalories + caloriesForFood
                    _profileData.value.snack = Snack(updatedSnackFoods, updatedCalories)
                    docRef.update("snack", _profileData.value.snack)
                }

                else -> return
            }
        } catch (e: Exception) {
            Log.e("SaveMeal", "Error saving meal: ${e.message}", e)
        }
        getMealData()
    }

    fun toggleWaterGlass(index: Int) {
        viewModelScope.launch {
            val uid = getCurrentUserUid() ?: return@launch
            val firestore = FirebaseFirestore.getInstance()
            val docRef = firestore.collection("profileData").document(uid)

            try {
                docRef.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val glassData = documentSnapshot.toObject(MealDataUIState::class.java)
                        glassData?.let {
                            val updatedWaterGlasses = glassData.waterGlasses.toMutableList()
                            updatedWaterGlasses[index] = !updatedWaterGlasses[index]
                            val updates = mapOf("waterGlasses" to updatedWaterGlasses)
                            docRef.update(updates).addOnSuccessListener {
                                Log.d("ToggleWaterGlass", "Water glass toggled successfully.")
                            }.addOnFailureListener { e ->
                                Log.e("ToggleWaterGlass", "Error toggling water glass: ${e.message}", e)
                            }
                        }
                    }
                }.await()
                getMealData()
            } catch (e: Exception) {
                Log.e("ToggleWaterGlass", "Error toggling water glass: ${e.message}", e)
            }
        }
    }
}