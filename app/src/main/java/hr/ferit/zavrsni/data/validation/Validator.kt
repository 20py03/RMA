package hr.ferit.zavrsni.data.validation

object Validator {

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

    fun validateName(name: String): ValidationResult {
        return if (!name.isNullOrEmpty() && name.length >= 3) {
            ValidationResult(status = true)
        } else {
            ValidationResult(status = false, errorMessage = "Name must be at least 3 characters long")
        }
    }

    fun validateEmail(email: String): ValidationResult {
        return if (email.matches(emailRegex)) {
            ValidationResult(status = true)
        } else {
            ValidationResult(status = false, errorMessage = "Invalid email address")
        }
    }

    fun validatePassword(pwd: String): ValidationResult {
        return if (!pwd.isNullOrEmpty() && pwd.length >= 6) {
            ValidationResult(status = true)
        } else {
            ValidationResult(status = false, errorMessage = "Password must be at least 6 characters long")
        }
    }

    fun validateConfirmPassword(pwd: String, confirmPwd: String): ValidationResult {
        return if (!confirmPwd.isNullOrEmpty() && confirmPwd == pwd) {
            ValidationResult(status = true)
        } else {
            ValidationResult(status = false, errorMessage = "Passwords do not match")
        }
    }
}

data class ValidationResult(
    val status: Boolean = false,
    val errorMessage: String? = null
)