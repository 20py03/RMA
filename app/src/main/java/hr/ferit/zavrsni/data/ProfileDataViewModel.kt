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

    fun getData() {
        viewModelScope.launch {
            val uid = getCurrentUserUid()
            if (uid != null) {
                state.value = getDataFromFireStore(uid)
            }
        }
    }

    private suspend fun getDataFromFireStore(uid: String): ProfileDataUIState {
        val db = FirebaseFirestore.getInstance()
        var data = ProfileDataUIState()

        try {
            val document = db.collection("profileData").document(uid).get().await()
            if (document.exists()) {
                data = document.toObject(ProfileDataUIState::class.java) ?: ProfileDataUIState()
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "getDataFromFireStore: $e")
        }
        return data
    }

    private suspend fun getCurrentUserUid(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

}

