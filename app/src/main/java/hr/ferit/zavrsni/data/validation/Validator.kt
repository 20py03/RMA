package hr.ferit.zavrsni.data.validation

object Validator {

    fun validateName(name:String) : ValidationResult{
        return ValidationResult(
            (!name.isNullOrEmpty() && name.length>=3)
        )
    }

    fun validateEmail(email:String) : ValidationResult{
        return ValidationResult(
            (!email.isNullOrEmpty())
        )
    }

    fun validatePassword(pwd:String) : ValidationResult{
        return ValidationResult(
            (!pwd.isNullOrEmpty() && pwd.length>=6)
        )
    }

    fun validateConfirmPassword(pwd: String, confirmPwd: String) : ValidationResult{
        return ValidationResult(
            (!confirmPwd.isNullOrEmpty() && confirmPwd == pwd)
        )
    }

}

data class ValidationResult(
    val status: Boolean = false

)

