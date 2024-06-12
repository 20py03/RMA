package hr.ferit.zavrsni.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FoodViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val foodCollection = db.collection("food")
    private val _allFoodItems = MutableStateFlow<List<Food>>(emptyList())
    val allFoodItems: StateFlow<List<Food>> get() = _allFoodItems


    init {
        if(_allFoodItems.value == emptyList<Food>())
        {
            getAllFoodItems()
        }
    }

    fun addFood(food: Food) {
        viewModelScope.launch {
            try {
                foodCollection.document(food.id).set(food).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getAllFoodItems() {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            val foodCollection = db.collection("food")
             try {
                val querySnapshot = foodCollection.get().await()
                _allFoodItems.value = querySnapshot.documents
                    .map { document ->
                        document.toObject(Food::class.java)!!
                    }
            } catch (e: Exception) {
                Log.d("error", "getAllFoodItems: $e")
                emptyList<Food>()
            }
        }
    }
}