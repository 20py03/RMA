package hr.ferit.zavrsni.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import hr.ferit.zavrsni.components.Footer
import hr.ferit.zavrsni.data.RecipeViewModel
import hr.ferit.zavrsni.data.Recipes.Recipe
import hr.ferit.zavrsni.ui.theme.DarkGray
import hr.ferit.zavrsni.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun RecipeScreen(navController: NavController) {
    val recipeViewModel: RecipeViewModel = viewModel()
    val recipesState = recipeViewModel.recipes.collectAsState()

    LaunchedEffect(Unit) {
        recipeViewModel.fetchRecipes("feca3b7189e0457ca2ceba2b6a089ee6")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(16.dp)
    ) {

        RecipeList(recipesState.value)
        Footer(navController)
    }
}

@Composable
fun RecipeList(recipes: List<Recipe>) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(recipes) { recipe->
                    RecipeItem(recipe)
            }
        }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Column {
        Text(text = "Title: ${recipe.title ?: "N/A"}")
        Text(text = "Servings: ${recipe.servings}")
        Text(text = "Ready in minutes: ${recipe.readyInMinutes}")
        Spacer(modifier = Modifier.size(10.dp)) // razmak
        // Add more details as needed
    }
}


