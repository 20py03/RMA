package hr.ferit.zavrsni.data

sealed class UIEvent {

    data class NameChanged(val name:String) : UIEvent()
    data class EmailChanged(val email:String) : UIEvent()
    data class PasswordChanged(val password:String) : UIEvent()
    data class ConfirmPasswordChanged(val confirmPassword:String) : UIEvent()
    object RegisterButtonClicked: UIEvent()

}