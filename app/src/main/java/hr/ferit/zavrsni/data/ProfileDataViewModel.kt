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

class ProfileDataViewModel : ViewModel(){
    val state = mutableStateOf(ProfileDataUIState())

    init {
        getData()
    }

    private fun getData(){
        viewModelScope.launch {
            val userName = getUserNameFromFirebase()
            state.value = getDataFromFireStore(userName)
        }
    }

    private suspend fun getDataFromFireStore(userName: String?) : ProfileDataUIState{
        val db = FirebaseFirestore.getInstance()
        var data = ProfileDataUIState()

        try{
            db.collection("profileData").get().await().map {
                val result = it.toObject(ProfileDataUIState::class.java)
                data = result.copy(name = userName ?: "")
            }
        }catch (e:FirebaseFirestoreException){
            Log.d("error","getDataFromFireStore: $e")
        }
        return data
    }
}
suspend fun getUserNameFromFirebase(): String? {
    return FirebaseAuth.getInstance().currentUser?.displayName
}

