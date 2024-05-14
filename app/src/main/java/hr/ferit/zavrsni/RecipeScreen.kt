package hr.ferit.zavrsni

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.ferit.zavrsni.ui.theme.Blue
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

        // Footer
        Footer()
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
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "AvoToast" )
    }
}

@Composable
fun Footer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick= {

            })
        {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.home_icon),
                contentDescription = "Right arrow",
                modifier = Modifier,
                tint = Blue
            )
        }
        IconButton(
            onClick= {

            })
        {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.challenge_target_icon),
                contentDescription = "Right arrow",
                modifier = Modifier,
                tint = Blue
            )
        }

        IconButton(
            onClick= {

            })
        {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.cooking_chef_cap_icon),
                contentDescription = "Right arrow",
                modifier = Modifier,
                tint = Blue
            )
        }

        IconButton(
            onClick= {

            })
        {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.person_profile_image_icon),
                contentDescription = "Right arrow",
                modifier = Modifier,
                tint = Blue
            )
        }
    }
}
