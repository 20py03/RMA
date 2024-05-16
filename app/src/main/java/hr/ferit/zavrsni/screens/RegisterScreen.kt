package hr.ferit.zavrsni.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import hr.ferit.zavrsni.components.MyTextFieldComponent
import hr.ferit.zavrsni.components.NormalTextComponent
import hr.ferit.zavrsni.components.PwdTextFieldComponent
import hr.ferit.zavrsni.data.LoginViewModel
import hr.ferit.zavrsni.data.UIEvent
import hr.ferit.zavrsni.ui.theme.White

@Composable
fun RegisterScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel ()) {

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

            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.name),
                painterResource(id = R.drawable.profile_1341_svgrepo_com),
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.NameChanged(it))
                },
                errorStatus = loginViewModel.registrationUIState.value.nameError
            )

            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.e_mail),
                painterResource = painterResource(id = R.drawable.mail_svgrepo_com),
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.EmailChanged(it))
                },
                errorStatus = loginViewModel.registrationUIState.value.emailError
            )

            PwdTextFieldComponent(
                labelValue = stringResource(id = R.string.pwd),
                painterResource = painterResource(id = R.drawable.lock_alt_svgrepo_com),
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.PasswordChanged(it))
                },
                errorStatus = loginViewModel.registrationUIState.value.passwordError
            )

            PwdTextFieldComponent(
                labelValue = stringResource(id = R.string.confirm_pwd),
                painterResource = painterResource(id = R.drawable.lock_alt_svgrepo_com),
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.ConfirmPasswordChanged(it))
                },
                errorStatus = loginViewModel.registrationUIState.value.confirmPasswordError
            )
            
            Spacer(modifier = Modifier.height(80.dp))

            ButtonComponent(value = stringResource(id = R.string.register), onButtonClicked = {loginViewModel.onEvent(UIEvent.RegisterButtonClicked)})

            Spacer(modifier = Modifier.height(20.dp))

            DividerComponent()

            ClicableLoginTextComponent (tryingToLogin = true, onTextSelected = {
                navController.navigate(route = AppNavigation.LoginScreen.route)
            })

        }
    }
}

@Preview
@Composable
fun DefaultPreviewOfRegisterScreen(){
    RegisterScreen(navController = rememberNavController())
}


    /*
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create your account",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Blue,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Confirm Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { navController.navigate("GenderScreen")},
                colors = ButtonDefaults.buttonColors(containerColor = Blue),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(56.dp)
                    .fillMaxWidth(0.7f)
                    .clip(RoundedCornerShape(8.dp)),
            ) {
                Text(
                    text = "Create",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
     */

