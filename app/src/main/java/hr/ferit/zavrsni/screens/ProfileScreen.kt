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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.google.firebase.auth.FirebaseAuth
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.R
import hr.ferit.zavrsni.components.ButtonComponent
import hr.ferit.zavrsni.data.ProfileDataUIState
import hr.ferit.zavrsni.data.ProfileDataViewModel
import hr.ferit.zavrsni.data.SignUpViewModel
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun ProfileScreen(
    navController: NavController,
    loginViewModel: SignUpViewModel = viewModel(),
    profileDataViewModel: ProfileDataViewModel = viewModel()
) {
    val getData = profileDataViewModel.state.value

    LaunchedEffect(Unit) {
        profileDataViewModel.getData()
    }

    val tdee = calculateTDEE(
        gender = getData.gender,
        age = getData.age,
        height = getData.height,
        weight = getData.weight,
        activity = getData.activity
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(imageResource = R.drawable.person_profile_image_icon)
            ButtonComponent(
                value = "Logout",
                onButtonClicked = {
                    loginViewModel.logout(navController)
                    // Reload profile data after logout
                    profileDataViewModel.getData()
                },
                isEnabled = true
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        ProfileInfo("Ime: ${getData.name}")
        ProfileInfo("Age: ${getData.age}")
        ProfileInfo("Gender: ${getData.gender}")
        ProfileInfo("Height: ${getData.height}")
        ProfileInfo("Weight: ${getData.weight}")
        ProfileInfo("Goal: ${getData.goal}")
        ProfileInfo("Activity: ${getData.activity}")
        ProfileInfo("TDEE: $tdee kcal")
        ProfileInfo("Kalorije: 2000 kcal")
        ProfileInfo("Proteini: 150 g")
        ProfileInfo("Ugljikohidrati: 200 g")
        ProfileInfo("Masti: 70 g")

        Spacer(modifier = Modifier.height(50.dp))
        Footer(navController = navController)
    }
}

fun calculateTDEE(gender: String, age: String, height: String, weight: String, activity: String): Double {
    val ageInt = age.toIntOrNull() ?: 0
    val heightDouble = height.toDoubleOrNull() ?: 0.0
    val weightDouble = weight.toDoubleOrNull() ?: 0.0

    val bmr = if (gender.lowercase() == "male") {
        10 * weightDouble + 6.25 * heightDouble - 5 * ageInt + 5
    } else {
        10 * weightDouble + 6.25 * heightDouble - 5 * ageInt - 161
    }

    val activityFactor = when (activity.lowercase()) {
        "sedentary" -> 1.2
        "lightly active" -> 1.375
        "moderately active" -> 1.55
        "very active" -> 1.725
        "athlete" -> 1.9
        else -> 1.2
    }

    return bmr * activityFactor
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
