package com.zen.cendakala.ui.auth.login

sealed class LoginUIEvent {

    data class EmailChanged(val email: String) : LoginUIEvent()
    data class PasswordChanged(val password: String) : LoginUIEvent()
    object LoginUIButtonClicked : LoginUIEvent()
}
