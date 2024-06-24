package hr.ferit.zavrsni.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hr.ferit.zavrsni.R
import hr.ferit.zavrsni.components.ButtonComponent
import hr.ferit.zavrsni.components.Footer
import hr.ferit.zavrsni.components.MyTextFieldComponent
import hr.ferit.zavrsni.data.LoginUIEvent
import hr.ferit.zavrsni.data.ProfileDataUIState
import hr.ferit.zavrsni.data.ProfileDataViewModel
import hr.ferit.zavrsni.data.ProfileUIEvent
import hr.ferit.zavrsni.data.SignUpViewModel
import hr.ferit.zavrsni.ui.theme.Blue
import hr.ferit.zavrsni.ui.theme.DarkGray
import hr.ferit.zavrsni.ui.theme.LightGray
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun EditProfileScreen(
    navController: NavController,
    profileDataViewModel: ProfileDataViewModel = viewModel()
) {
    val getData = profileDataViewModel.profileUIState.value

    LaunchedEffect(Unit) {
        profileDataViewModel.getProfileData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(20.dp)
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Age") },
            colors = TextFieldDefaults.colors(
                focusedLabelColor = Blue,
                cursorColor = DarkGray,
                focusedTextColor = Blue,
                unfocusedTextColor = Blue,
                focusedContainerColor = White,
                unfocusedContainerColor = LightGray,
                errorContainerColor = White,
                errorTextColor = DarkGray
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true,
            maxLines = 1,
            value = getData.age,
            onValueChange = {
                profileDataViewModel.onEvent(ProfileUIEvent.AgeChanged(it), navController)
            },
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Height") },
            colors = TextFieldDefaults.colors(
                focusedLabelColor = Blue,
                cursorColor = DarkGray,
                focusedTextColor = Blue,
                unfocusedTextColor = Blue,
                focusedContainerColor = White,
                unfocusedContainerColor = LightGray,
                errorContainerColor = White,
                errorTextColor = DarkGray
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true,
            maxLines = 1,
            value = getData.height,
            onValueChange = {
                profileDataViewModel.onEvent(ProfileUIEvent.HeightChanged(it), navController)
            },
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Weight") },
            colors = TextFieldDefaults.colors(
                focusedLabelColor = Blue,
                cursorColor = DarkGray,
                focusedTextColor = Blue,
                unfocusedTextColor = Blue,
                focusedContainerColor = White,
                unfocusedContainerColor = LightGray,
                errorContainerColor = White,
                errorTextColor = DarkGray
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true,
            maxLines = 1,
            value = getData.weight,
            onValueChange = {
                profileDataViewModel.onEvent(ProfileUIEvent.WeightChanged(it), navController)
            },
        )
        val goals = listOf(
            "Lose weight",
            "Build muscle",
            "Get leaner",
            "Flexibility",
            "Improve health"
        )
        ActivityLevelDropdown(goals,profileDataViewModel, navController,
            OnClick = {
                profileDataViewModel.onEvent(ProfileUIEvent.GoalChanged(it), navController)
            })
        val activityLevels = listOf(
            "Sedentary",
            "Lightly Active",
            "Moderately Active",
            "Very Active",
            "Athlete"
        )
        ActivityLevelDropdown(activityLevels,profileDataViewModel, navController, OnClick = {
            profileDataViewModel.onEvent(ProfileUIEvent.ActivityChanged(it), navController)
        })
        ButtonComponent(
            value = "Save",
            onButtonClicked = {
                profileDataViewModel.editProfile(navController)
                profileDataViewModel.getProfileData()
            },
            isEnabled = true
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Footer(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityLevelDropdown(dropdownLevels: List<String>, profileDataViewModel: ProfileDataViewModel, navController: NavController, OnClick: (String) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(dropdownLevels[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedText,
            onValueChange = { },
            readOnly = true,
            label = { Text("Activity Level") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            dropdownLevels.forEach { activityLevel ->
                DropdownMenuItem(
                    text = { Text(activityLevel) },
                    onClick = {
                        selectedText = activityLevel
                        expanded = false
                        OnClick(activityLevel)
                    }
                )
            }
        }
    }
}
