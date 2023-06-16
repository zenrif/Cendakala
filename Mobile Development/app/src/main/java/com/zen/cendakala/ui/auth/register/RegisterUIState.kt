package com.zen.cendakala.ui.auth.register

import com.zen.cendakala.ui.components.Category

data class RegisterUIState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val gender: String = "",
    val job: String = "",
    val interest: List<Category> = listOf(),

    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var nameError: Boolean = false,
    var genderError: Boolean = false,
    var jobError: Boolean = false,
    var interestError: Boolean = false,
)
