package hr.ferit.zavrsni.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.State
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileDataViewModel : ViewModel() {
    val state = mutableStateOf(ProfileDataUIState())
    val energyDataViewModel = EnergyDataViewModel()

    private val _profileData = mutableStateOf(ProfileDataUIState(
        breakfast = Breakfast(),
        lunch = Lunch(),
        dinner = Dinner(),
        snack = Snack()
    ))
    val profileData: State<ProfileDataUIState> = _profileData

    fun addFoodToMeal(mealType: Int, food: Food, calories: Int) {
        _profileData.value = _profileData.value.copy(
            breakfast = if (mealType == 0) Breakfast(_profileData.value.breakfast.breakfastFoods.plus(food)) else _profileData.value.breakfast,
            lunch = if (mealType == 1) Lunch(_profileData.value.lunch.lunchFoods.plus(food)) else _profileData.value.lunch,
            dinner = if (mealType == 2) Dinner(_profileData.value.dinner.dinnerFoods.plus(food)) else _profileData.value.dinner,
            snack = if (mealType == 3) Snack(_profileData.value.snack.snackFoods.plus(food)) else _profileData.value.snack
        )
    }

    fun toggleWaterGlass(index: Int) {
        viewModelScope.launch {
            val uid = getCurrentUserUid() ?: return@launch
            val firestore = FirebaseFirestore.getInstance()
            val docRef = firestore.collection("profileData").document(uid)

            try {
                docRef.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val profileData = documentSnapshot.toObject(ProfileDataUIState::class.java)
                        profileData?.let {
                            val updatedWaterGlasses = profileData.waterGlasses.toMutableList()
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
            } catch (e: Exception) {
                Log.e("ToggleWaterGlass", "Error toggling water glass: ${e.message}", e)
            }
        }
    }


    private suspend fun updateProfileDataInFirestore(uid: String, updates: Map<String, Any>) {
        val db = FirebaseFirestore.getInstance()

        try {
            db.collection("profileData").document(uid).update(updates).await()
            Log.d("success", "Profile data updated successfully.")
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "updateProfileDataInFirestore: $e")
        }
    }

    fun removeFoodFromMeal(mealType: Int, food: Food) {
        viewModelScope.launch {
            val uid = getCurrentUserUid()
            if (uid != null) {
                val updatedData = when (mealType) {
                    0 -> {
                        val newFoods: List<Food> = _profileData.value.breakfast.breakfastFoods.filter { it != food }
                        _profileData.value.breakfast.breakfastFoods = newFoods
                        _profileData.value
                    }
                    1 -> {
                        val newFoods: List<Food> = _profileData.value.lunch.lunchFoods.filter { it != food }
                        _profileData.value.lunch.lunchFoods = newFoods
                        _profileData.value
                    }
                    2 -> {
                        val newFoods: List<Food> = _profileData.value.dinner.dinnerFoods.filter { it != food }
                        _profileData.value.dinner.dinnerFoods = newFoods
                        _profileData.value
                    }
                    3 -> {
                        val newFoods: List<Food> = _profileData.value.snack.snackFoods.filter { it != food }
                        _profileData.value.snack.snackFoods = newFoods
                        _profileData.value
                    }
                    else -> _profileData.value
                }
                _profileData.value = updatedData

                val updates = when (mealType) {
                    0 -> mapOf("breakfast" to updatedData.breakfast)
                    1 -> mapOf("lunch" to updatedData.lunch)
                    2 -> mapOf("dinner" to updatedData.dinner)
                    3 -> mapOf("snack" to updatedData.snack)
                    else -> emptyMap()
                }
                updateProfileDataInFirestore(uid, updates)
            }
        }
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

    suspend fun saveMeal(mealType: Int, food: Food, totalCalories: Int) {
        val uid = getCurrentUserUid() ?: return
        val firestore = FirebaseFirestore.getInstance()
        val docRef = firestore.collection("profileData").document(uid)
        val breakfastFoods: List<Food> = _profileData.value.breakfast.breakfastFoods
        val lunchFoods: List<Food> = _profileData.value.lunch.lunchFoods
        val dinnerFoods: List<Food> = _profileData.value.dinner.dinnerFoods
        val snackFoods: List<Food> = _profileData.value.snack.snackFoods
        try {
            when (mealType) {
                0 -> {
                    _profileData.value.breakfast = Breakfast(
                        breakfastFoods.plus(food),
                        totalCalories
                    )
                    docRef.update("breakfast", _profileData.value.breakfast)
                }

                1 -> {
                    _profileData.value.lunch = Lunch(
                        lunchFoods.plus(food),
                        totalCalories
                    )
                    docRef.update("lunch", _profileData.value.lunch)
                }

                2 -> {
                    _profileData.value.dinner = Dinner(
                        dinnerFoods.plus(food),
                        totalCalories
                    )
                    docRef.update("dinner", _profileData.value.dinner)
                }
                3 -> {
                    _profileData.value.snack = Snack(
                        snackFoods.plus(food),
                        totalCalories
                    )
                    docRef.update("snack", _profileData.value.snack)
                }

                else -> return
            }
        }catch (e: Exception) {
            Log.e("SaveMeal", "Error saving meal: ${e.message}", e)
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
