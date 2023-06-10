package com.zen.cendakala.ui.auth.register

import com.zen.cendakala.ui.components.Category

sealed class RegisterUIEvent{

    data class EmailChanged(val email:String): RegisterUIEvent()
    data class PasswordChanged(val password: String) : RegisterUIEvent()
    data class NameChanged(val name: String) : RegisterUIEvent()
    data class GenderChanged(val gender: String) : RegisterUIEvent()
    data class JobChanged(val job: String) : RegisterUIEvent()
    data class InterestChanged(val interest: List<Category>) : RegisterUIEvent()
    object RegisterButtonClicked : RegisterUIEvent()

}
