package com.zen.cendakala.data.rules

import com.zen.cendakala.ui.components.Category

object Validator {
    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            (email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (password.isNotEmpty() && password.length >= 6)
        )
    }

    fun validateName(name: String): ValidationResult {
        return ValidationResult(
            (name.isNotEmpty() && name.length >= 3)
        )
    }

    fun validateGender(gender: String): ValidationResult {
        return ValidationResult(
            (gender.isNotEmpty())
        )
    }
    fun validateJob(job: String): ValidationResult {
        return ValidationResult(
            (job.isNotEmpty())
        )
    }

    fun validateInterest(interest: List<Category>): ValidationResult {
        return ValidationResult(
            (interest.isNotEmpty())
        )
    }
}

data class ValidationResult(
    val status: Boolean = false
)








