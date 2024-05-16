package hr.ferit.zavrsni.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import hr.ferit.zavrsni.data.validation.Validator
import kotlin.math.log

class LoginViewModel : ViewModel(){

    private val TAG = LoginViewModel::class.simpleName

    var registrationUIState = mutableStateOf(RegistrationUIState())

    fun onEvent(event: UIEvent){

        validateData()
        when(event){

            is UIEvent.NameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    name = event.name
                )
                printState()
            }

            is UIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
                printState()
            }

            is UIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
                printState()
            }

            is UIEvent.ConfirmPasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    confirmPassword = event.confirmPassword
                )
                printState()
            }

            is UIEvent.RegisterButtonClicked -> {
                signUp()
            }
        }

    }

    private fun signUp() {
        Log.d(TAG, "signUp")
        printState()
        validateData()
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

    }


    private fun printState(){
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }
}