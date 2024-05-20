package hr.ferit.zavrsni.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import hr.ferit.zavrsni.AppNavigation
import hr.ferit.zavrsni.data.validation.Validator

class SignUpViewModel : ViewModel(){

    private val TAG = SignUpViewModel::class.simpleName

    var registrationUIState = mutableStateOf(RegistrationUIState())

    var allValidationsPassed = mutableStateOf(false)

    var singUpInProgress = mutableStateOf(false)


    fun onEvent(event: SignUpUIEvent, navController: NavController){

        validateData()
        when(event){

            is SignUpUIEvent.NameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    name = event.name
                )
                printState()
            }

            is SignUpUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
                printState()
            }

            is SignUpUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
                printState()
            }

            is SignUpUIEvent.ConfirmPasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    confirmPassword = event.confirmPassword
                )
                printState()
            }

            is SignUpUIEvent.RegisterButtonClicked -> {
                signUp(navController)
            }

            else -> {}
        }

    }

    private fun signUp(navController: NavController) {
        Log.d(TAG, "signUp")
        printState()
        createUserInFirebase(
            email=registrationUIState.value.email,
            password=registrationUIState.value.password,
            navController = navController
        )
    }

    private fun validateData() {
        val nameResult = Validator.validateName(name = registrationUIState.value.name)
        val emailResult = Validator.validateEmail(email = registrationUIState.value.email)
        val pwdResult = Validator.validatePassword(pwd = registrationUIState.value.password)
        val confirmPwdResult = Validator.validateConfirmPassword(pwd = registrationUIState.value.password, confirmPwd = registrationUIState.value.confirmPassword)
        Log.d(TAG, "InsideValidateData")
        Log.d(TAG, "Name= $nameResult")
        Log.d(TAG, "Email= $emailResult")
        Log.d(TAG, "Pwd= $pwdResult")
        Log.d(TAG, "ConfirmPwd= $confirmPwdResult")

        registrationUIState.value=registrationUIState.value.copy(
            nameError = nameResult.status,
            emailError = emailResult.status,
            passwordError = pwdResult.status,
            confirmPasswordError = confirmPwdResult.status,
        )

        if(nameResult.status && emailResult.status && pwdResult.status && confirmPwdResult.status){
            allValidationsPassed.value = true
        }else{
            allValidationsPassed.value=false
        }
    }
    private fun printState(){
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }

    private fun createUserInFirebase(email:String, password:String, navController: NavController){

        singUpInProgress.value = true

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                Log.d(TAG,"Inside_OnCompleteListener")
                Log.d(TAG,"IsSuccessful = ${it.isSuccessful}")

                singUpInProgress.value = false

                if(it.isSuccessful){
                    navController.navigate(route =  AppNavigation.GenderScreen.route)
                }

            }
            .addOnFailureListener{
                Log.d(TAG,"Inside_OnFailureListener")
                Log.d(TAG,"Exception = ${it.message}")
                Log.d(TAG,"Exception = ${it.localizedMessage}")

            }
    }

    fun logout(navController: NavController){

        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val authStateListener = AuthStateListener{
            if(it.currentUser == null){
                Log.d(TAG, "Inside signOut success")
                navController.navigate(route =  AppNavigation.LoginScreen.route)
            }else{
                Log.d(TAG, "Inside signOut is not complete")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)

    }
}