package hr.ferit.zavrsni.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.data.validation.Validator

class LoginViewModel: ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress  = mutableStateOf(false)


    var currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser


    fun onEvent(event:LoginUIEvent, navController: NavController){
        when(event) {

            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login(navController)
            }

        }

        validateLoginData()
    }

    private fun validateLoginData() {
        val emailResult = Validator.validateEmail(email = loginUIState.value.email)
        val pwdResult = Validator.validatePassword(pwd = loginUIState.value.password)
        Log.d(TAG, "InsideValidateData")
        Log.d(TAG, "Email= $emailResult")
        Log.d(TAG, "Pwd= $pwdResult")

        loginUIState.value=loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = pwdResult.status,
        )

        if(emailResult.status && pwdResult.status){
            allValidationsPassed.value = true
        }else{
            allValidationsPassed.value=false
        }
    }


    private fun login(navController: NavController) {

        loginInProgress.value = true

        val email = loginUIState.value.email
        val password = loginUIState.value.password

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG,"Inside_OnCompleteListener")
                Log.d(TAG,"IsSuccessful = ${it.isSuccessful}")

                loginInProgress.value = false

                if(it.isSuccessful){
                    currentUser = FirebaseAuth.getInstance().currentUser
                    navController.navigate(route =  AppNavigation.HomeScreen.route){
                        popUpTo(AppNavigation.LoginScreen.route) { inclusive = true }
                    }
                }
            }
            .addOnFailureListener{
                Log.d(TAG,"Inside_OnFailureListener")
                Log.d(TAG,"Exception = ${it.message}")
                Log.d(TAG,"Exception = ${it.localizedMessage}")
                loginInProgress.value = false
            }
    }
}