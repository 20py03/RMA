package hr.ferit.zavrsni.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            state.value = getDataFromFireStore()
        }
    }

}

suspend fun getDataFromFireStore() : ProfileDataUIState{
    val db = FirebaseFirestore.getInstance()
    var data = ProfileDataUIState()

    try{
        db.collection("profileData").get().await().map {
            val result = it.toObject(ProfileDataUIState::class.java)
            data = result
        }
    }catch (e:FirebaseFirestoreException){
        Log.d("error","getDataFromFireStore: $e")
    }
    return data
}
