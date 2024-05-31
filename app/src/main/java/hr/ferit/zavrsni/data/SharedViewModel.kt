package hr.ferit.zavrsni.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val mealType = mutableStateOf(0)
}