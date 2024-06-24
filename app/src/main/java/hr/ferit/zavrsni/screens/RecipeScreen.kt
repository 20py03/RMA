package hr.ferit.zavrsni.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hr.ferit.zavrsni.components.Footer
import hr.ferit.zavrsni.components.RecipeList
import hr.ferit.zavrsni.data.RecipeViewModel
import hr.ferit.zavrsni.ui.theme.White

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

        RecipeList(recipesState.value, recipeViewModel)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Footer(navController)
    }
}


