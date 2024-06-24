package hr.ferit.zavrsni.data

data class RegistrationUIState (
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val nameError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val confirmPasswordError: Boolean = false,
    val errorText: String = ""
)