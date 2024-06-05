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
import hr.ferit.zavrsni.components.Footer
import hr.ferit.zavrsni.components.ProfileImage
import hr.ferit.zavrsni.components.ProfileInfo
import hr.ferit.zavrsni.data.ProfileDataUIState
import hr.ferit.zavrsni.data.ProfileDataViewModel
import hr.ferit.zavrsni.data.SignUpViewModel
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.DarkBlue
import hr.ferit.zavrsni.ui.theme.DarkGray
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun ProfileScreen(
    navController: NavController,
    loginViewModel: SignUpViewModel = viewModel(),
    profileDataViewModel: ProfileDataViewModel = viewModel()
) {
    val getData = profileDataViewModel.state.value
    val energyData = profileDataViewModel.energyDataViewModel.state.value

    LaunchedEffect(Unit) {
        profileDataViewModel.getProfileData()
    }

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
                    profileDataViewModel.getProfileData()
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
        ProfileInfo("TDEE: ${energyData.tdee} kcal")
        ProfileInfo("Calories intake: ${energyData.goalCalories} kcal")
        ProfileInfo("Proteins: ${energyData.protein} g")
        ProfileInfo("Carbs: ${energyData.carbohydrates} g")
        ProfileInfo("Fats: ${energyData.fat} g")

        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Footer(navController)
        }
    }
}









