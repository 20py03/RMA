package hr.ferit.zavrsni.data

sealed class SignUpUIEvent {

    data class NameChanged(val name:String) : SignUpUIEvent()
    data class EmailChanged(val email:String) : SignUpUIEvent()
    data class PasswordChanged(val password:String) : SignUpUIEvent()
    data class ConfirmPasswordChanged(val confirmPassword:String) : SignUpUIEvent()
    object RegisterButtonClicked: SignUpUIEvent()

}