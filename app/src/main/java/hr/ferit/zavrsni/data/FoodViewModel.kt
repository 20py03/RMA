package hr.ferit.zavrsni.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FoodViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val foodCollection = db.collection("food")

    fun addFood(food: Food) {
        viewModelScope.launch {
            try {
                foodCollection.document(food.id).set(food).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}