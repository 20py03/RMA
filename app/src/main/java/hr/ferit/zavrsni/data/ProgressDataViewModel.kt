package hr.ferit.zavrsni.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProgressDataViewModel : ViewModel() {

    val state = mutableStateOf(ProgressDataUIState())

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

    private suspend fun getCurrentUserUid(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    fun getProgressData() {
        viewModelScope.launch {
            val uid = getCurrentUserUid()
            if (uid != null) {
                val data = getDataFromFirestore(uid)
                state.value = data
            }
        }
    }

    private suspend fun getDataFromFirestore(uid: String): ProgressDataUIState {
        val db = FirebaseFirestore.getInstance()
        var data = ProgressDataUIState()

        try {
            val document = db.collection("profileData").document(uid).get().await()
            if (document.exists()) {
                data = document.toObject(ProgressDataUIState::class.java) ?: ProgressDataUIState()
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "getDataFromFirestore: $e")
        }
        return data
    }
}
