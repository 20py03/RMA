package hr.ferit.zavrsni.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.R
import hr.ferit.zavrsni.components.ButtonComponent
import hr.ferit.zavrsni.components.ClicableLoginTextComponent
import hr.ferit.zavrsni.components.ConfirmPwdTextFieldComponent
import hr.ferit.zavrsni.components.DividerComponent
import hr.ferit.zavrsni.components.HeadingTextComponent
import hr.ferit.zavrsni.components.MyTextFieldComponent
import hr.ferit.zavrsni.components.NormalTextComponent
import hr.ferit.zavrsni.components.PwdTextFieldComponent
import hr.ferit.zavrsni.data.RegistrationUIState
import hr.ferit.zavrsni.data.SignUpViewModel
import hr.ferit.zavrsni.data.SignUpUIEvent
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun RegisterScreen(navController: NavHostController, registerViewModel: SignUpViewModel = viewModel ()) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Surface(
            color = White,
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()){
                NormalTextComponent(value = stringResource(id = R.string.hello) )

                HeadingTextComponent(value = stringResource(id = R.string.create_account))

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = if(!registerViewModel.registrationUIState.value.errorText.isNullOrEmpty()) registerViewModel.registrationUIState.value.errorText else "",
                    color = Color.Red)

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.name),
                    painterResource(id = R.drawable.profile_1341_svgrepo_com),
                    onTextSelected = {
                        registerViewModel.onEvent(SignUpUIEvent.NameChanged(it), navController)
                    },
                    errorStatus = registerViewModel.registrationUIState.value.nameError
                )

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.e_mail),
                    painterResource = painterResource(id = R.drawable.mail_svgrepo_com),
                    onTextSelected = {
                        registerViewModel.onEvent(SignUpUIEvent.EmailChanged(it), navController)
                    },
                    errorStatus = registerViewModel.registrationUIState.value.emailError
                )

                PwdTextFieldComponent(
                    labelValue = stringResource(id = R.string.pwd),
                    painterResource = painterResource(id = R.drawable.lock_alt_svgrepo_com),
                    onTextSelected = {
                        registerViewModel.onEvent(SignUpUIEvent.PasswordChanged(it), navController)
                    },
                    errorStatus = registerViewModel.registrationUIState.value.passwordError
                )

                ConfirmPwdTextFieldComponent(
                    labelValue = stringResource(id = R.string.confirm_pwd),
                    painterResource = painterResource(id = R.drawable.lock_alt_svgrepo_com),
                    onTextSelected = {
                        registerViewModel.onEvent(SignUpUIEvent.ConfirmPasswordChanged(it), navController)
                    },
                    errorStatus = registerViewModel.registrationUIState.value.confirmPasswordError
                )

                Spacer(modifier = Modifier.height(80.dp))

                ButtonComponent(value = stringResource(id = R.string.register),
                    onButtonClicked = {
                        registerViewModel.onEvent(SignUpUIEvent.RegisterButtonClicked, navController)
                    },
                    isEnabled = registerViewModel.allValidationsPassed.value
                )

                Spacer(modifier = Modifier.height(20.dp))

                DividerComponent()

                ClicableLoginTextComponent (tryingToLogin = true, onTextSelected = {
                    navController.navigate(route = AppNavigation.LoginScreen.route)
                })

            }
        }

        if(registerViewModel.singUpInProgress.value) {
            CircularProgressIndicator()
        }

    }
}


