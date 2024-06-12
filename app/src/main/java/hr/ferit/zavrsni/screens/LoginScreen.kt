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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import hr.ferit.zavrsni.components.DividerComponent
import hr.ferit.zavrsni.components.HeadingTextComponent
import hr.ferit.zavrsni.components.LoginPwdTextFieldComponent
import hr.ferit.zavrsni.components.MyTextFieldComponent
import hr.ferit.zavrsni.components.NormalTextComponent
import hr.ferit.zavrsni.data.LoginUIEvent
import hr.ferit.zavrsni.data.LoginViewModel
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel ()){

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = White,
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                NormalTextComponent(value = stringResource(id = R.string.hello))

                HeadingTextComponent(value = stringResource(id = R.string.welcome_back))

                Spacer(modifier = Modifier.height(80.dp))

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.e_mail),
                    painterResource = painterResource(id = R.drawable.mail_svgrepo_com),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it), navController)
                    },
                    errorStatus = loginViewModel.loginUIState.value.emailError
                )

                LoginPwdTextFieldComponent(
                    labelValue = stringResource(id = R.string.pwd),
                    painterResource = painterResource(id = R.drawable.lock_alt_svgrepo_com),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it), navController)
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )

                Spacer(modifier = Modifier.height(50.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(
                            LoginUIEvent.LoginButtonClicked,
                            navController
                        )
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value
                )

                Spacer(modifier = Modifier.height(30.dp))

                DividerComponent()

                Spacer(modifier = Modifier.height(20.dp))

                ClicableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                    navController.navigate(route = AppNavigation.RegisterScreen.route)
                })

            }
        }
        if(loginViewModel.loginInProgress.value) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(navController = rememberNavController())
}