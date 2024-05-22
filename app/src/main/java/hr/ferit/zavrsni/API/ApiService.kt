package hr.ferit.zavrsni.API

import hr.ferit.zavrsni.data.Recipes.RandomRecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int,
    ): RandomRecipeResponse

}