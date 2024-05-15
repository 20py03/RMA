package hr.ferit.zavrsni.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.LightPink
import hr.ferit.zavrsni.ui.theme.White


@Composable
fun RecipeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        RecipeCategory("Breakfast")
        RecipeCategory("Lunch")
        RecipeCategory("Dinner")
        RecipeCategory("Snacks")

        Footer(navController = navController)
    }
}

@Composable
fun RecipeCategory(category: String) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = category,
            color = Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            content = {
                items(3) {
                    RecipeImage()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RecipeImage() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color = LightPink)
            .padding(8.dp)
    ) {

    }
}

