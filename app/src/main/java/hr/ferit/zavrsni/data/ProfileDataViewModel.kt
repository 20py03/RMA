package hr.ferit.zavrsni.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileDataViewModel : ViewModel() {
    val state = mutableStateOf(ProfileDataUIState())
    val energyDataViewModel = EnergyDataViewModel()

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
}
