package hr.ferit.zavrsni.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.R
import hr.ferit.zavrsni.components.ButtonComponent
import hr.ferit.zavrsni.data.SignUpViewModel
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun ProfileScreen(navController: NavController, loginViewModel: SignUpViewModel = viewModel ()){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(20.dp)
    ) {
        ProfileImage(imageResource = R.drawable.person_profile_image_icon)
        Spacer(modifier = Modifier.height(20.dp))
        ProfileInfo("Ime: John Doe")
        ProfileInfo("Visina: 180 cm")
        ProfileInfo("Težina: 75 kg")
        ProfileInfo("Cilj: Mršavljenje")
        ProfileInfo("TDEE: 2500 kcal")
        ProfileInfo("Kalorije: 2000 kcal")
        ProfileInfo("Proteini: 150 g")
        ProfileInfo("Ugljikohidrati: 200 g")
        ProfileInfo("Masti: 70 g")
        Spacer(modifier = Modifier.height(50.dp))

        ButtonComponent(value = "Logout",
            onButtonClicked = {
                loginViewModel.logout(navController)
            },
            isEnabled = true
        )

        Spacer(modifier = Modifier.height(60.dp))
        Footer(navController = navController)
    }
}

@Composable
fun ProfileImage(imageResource: Int) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(color = Color.White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = imageResource),
            contentDescription = "Profile picture",
            modifier = Modifier.size(100.dp),
            tint = Blue
        )
    }
}

@Composable
fun ProfileInfo(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Blue,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
fun Footer(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FooterIcon(imageVector = ImageVector.vectorResource(id = R.drawable.home_icon)) {
            navController.navigate(route = AppNavigation.HomeScreen.route)
        }
        FooterIcon(imageVector = ImageVector.vectorResource(id = R.drawable.challenge_target_icon)) {
            navController.navigate(route = AppNavigation.ActivityScreen.route)
        }
        FooterIcon(imageVector = ImageVector.vectorResource(id = R.drawable.cooking_chef_cap_icon)) {
            navController.navigate(route = AppNavigation.RecipeScreen.route)
        }
        FooterIcon(imageVector = ImageVector.vectorResource(id = R.drawable.person_profile_image_icon)) {
            navController.navigate(route = AppNavigation.ProfileScreen.route)
        }
    }
}

@Composable
fun FooterIcon(imageVector: ImageVector, onClick: () -> Unit) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = Blue,
        modifier = Modifier
            .size(40.dp)
            .clickable(onClick = onClick)
    )
}
