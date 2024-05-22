package hr.ferit.zavrsni.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.ferit.zavrsni.API.ApiClient
import hr.ferit.zavrsni.API.ApiService
import hr.ferit.zavrsni.data.Recipes.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val apiService: ApiService = ApiClient.retrofit.create(ApiService::class.java)
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    fun fetchRecipes(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getRandomRecipes(apiKey, 10)
                if(response.recipes.isNotEmpty())
                {
                    _recipes.value = response.recipes
                }
            } catch (e: Exception) {
                // handle error
            }
        }
    }
}