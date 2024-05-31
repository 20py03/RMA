package hr.ferit.zavrsni.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.State
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileDataViewModel : ViewModel() {
    val state = mutableStateOf(ProfileDataUIState())
    val energyDataViewModel = EnergyDataViewModel()

    private val _profileData = mutableStateOf(ProfileDataUIState(
        breakfastFoods = emptyList(),
        lunchFoods = emptyList(),
        dinnerFoods = emptyList(),
        snackFoods = emptyList(),
        breakfastCalories = 0,
        lunchCalories = 0,
        dinnerCalories = 0,
        snackCalories = 0
    ))
    val profileData: State<ProfileDataUIState> = _profileData

    fun addFoodToMeal(mealType: Int, food: Food, calories: Int) {
        _profileData.value = _profileData.value.copy(
            breakfastFoods = if (mealType == 0) _profileData.value.breakfastFoods + food else _profileData.value.breakfastFoods,
            lunchFoods = if (mealType == 1) _profileData.value.lunchFoods + food else _profileData.value.lunchFoods,
            dinnerFoods = if (mealType == 2) _profileData.value.dinnerFoods + food else _profileData.value.dinnerFoods,
            snackFoods = if (mealType == 3) _profileData.value.snackFoods + food else _profileData.value.snackFoods,
            breakfastCalories = if (mealType == 0) _profileData.value.breakfastCalories + calories else _profileData.value.breakfastCalories,
            lunchCalories = if (mealType == 1) _profileData.value.lunchCalories + calories else _profileData.value.lunchCalories,
            dinnerCalories = if (mealType == 2) _profileData.value.dinnerCalories + calories else _profileData.value.dinnerCalories,
            snackCalories = if (mealType == 3) _profileData.value.snackCalories + calories else _profileData.value.snackCalories
        )
    }

    fun loadDataFromFirestore(mealType: Int) {
        viewModelScope.launch {
            val uid = getCurrentUserUid()
            if (uid != null) {
                val data = hr.ferit.zavrsni.screens.getDataFromFirestore(uid, mealType)
                _profileData.value = data
            }
        }
    }

    fun getData() {
        viewModelScope.launch {
            val uid = getCurrentUserUid()
            if (uid != null) {
                val data = getDataFromFirestore(uid)
                energyDataViewModel.calculateEnergyData(
                    gender = data.gender,
                    age = data.age,
                    height = data.height,
                    weight = data.weight,
                    activity = data.activity,
                    goal = data.goal
                )
                state.value = data
            }
        }
    }

    fun saveNotes(note1: String, note2: String) {
        viewModelScope.launch {
            val uid = getCurrentUserUid()
            if (uid != null) {
                saveNotesToFirestore(uid, note1, note2)
            }
        }
    }

    fun saveWeights(beforeWeight: String, currentWeight: String, afterWeight: String) {
        viewModelScope.launch {
            val uid = getCurrentUserUid()
            if (uid != null) {
                saveWeightsToFirestore(uid, beforeWeight, currentWeight, afterWeight)
            }
        }
    }

    fun saveEnergyData() {
        viewModelScope.launch {
            val uid = getCurrentUserUid()
            if (uid != null) {
                val energyData = energyDataViewModel.state.value
                saveEnergyDataToFirestore(uid, energyData)
            }
        }
    }

    private suspend fun getDataFromFirestore(uid: String): ProfileDataUIState {
        val db = FirebaseFirestore.getInstance()
        var data = ProfileDataUIState()

        try {
            val document = db.collection("profileData").document(uid).get().await()
            if (document.exists()) {
                data = document.toObject(ProfileDataUIState::class.java) ?: ProfileDataUIState()
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "getDataFromFirestore: $e")
        }
        return data
    }

    private suspend fun getCurrentUserUid(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    private suspend fun saveEnergyDataToFirestore(uid: String, energyData: EnergyDataUIState) {
        val db = FirebaseFirestore.getInstance()

        try {
            db.collection("profileData").document(uid).set(energyData).await()
            Log.d("success", "Energy data saved successfully.")
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "saveEnergyDataToFirestore: $e")
        }
    }

    private suspend fun saveNotesToFirestore(uid: String, note1: String, note2: String) {
        val db = FirebaseFirestore.getInstance()
        val notes = hashMapOf(
            "note1" to note1,
            "note2" to note2
        )

        try {
            db.collection("profileData").document(uid).update(notes as Map<String, Any>).await()
            Log.d("success", "Notes updated successfully.")
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "saveNotesToFirestore: $e")
        }
    }

    private suspend fun saveWeightsToFirestore(uid: String, beforeWeight: String, currentWeight: String, afterWeight: String) {
        val db = FirebaseFirestore.getInstance()
        val weights = hashMapOf(
            "beforeWeight" to beforeWeight,
            "currentWeight" to currentWeight,
            "afterWeight" to afterWeight
        )

        try {
            db.collection("profileData").document(uid).update(weights as Map<String, Any>).await()
            Log.d("success", "Weights updated successfully.")
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "saveWeightsToFirestore: $e")
        }
    }

    fun saveMeal(mealType: Int, food: Food, totalCalories: Int) {
        viewModelScope.launch {
            val uid = getCurrentUserUid()
            if (uid != null) {
                val currentData = getDataFromFirestore(uid)
                val updatedData = when (mealType) {
                    0 -> currentData.copy(
                        breakfastCalories = currentData.breakfastCalories + totalCalories,
                        breakfastFoods = currentData.breakfastFoods + food
                    )
                    1 -> currentData.copy(
                        lunchCalories = currentData.lunchCalories + totalCalories,
                        lunchFoods = currentData.lunchFoods + food
                    )
                    2 -> currentData.copy(
                        dinnerCalories = currentData.dinnerCalories + totalCalories,
                        dinnerFoods = currentData.dinnerFoods + food
                    )
                    3 -> currentData.copy(
                        snackCalories = currentData.snackCalories + totalCalories,
                        snackFoods = currentData.snackFoods + food
                    )
                    else -> currentData
                }
                saveDataToFirestore(uid, updatedData)
            }
        }
    }

    private suspend fun saveDataToFirestore(uid: String, profileData: ProfileDataUIState) {
        val db = FirebaseFirestore.getInstance()

        try {
            db.collection("profileData").document(uid).set(profileData).await()
            Log.d("success", "Profile data saved successfully.")
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "saveDataToFirestore: $e")
        }
    }
}
