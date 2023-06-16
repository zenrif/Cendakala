package com.zen.cendakala.ui.profile

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.zen.cendakala.data.Result
import com.zen.cendakala.data.model.LoginModel
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.responses.LoginResponse
import com.zen.cendakala.data.responses.UserResponse
import com.zen.cendakala.data.rules.Validator
import com.zen.cendakala.data.source.local.UserPreference
import com.zen.cendakala.ui.auth.login.LoginUIEvent
import com.zen.cendakala.ui.auth.login.LoginUIState
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModel(
    private val repo: SurveyRepository,
) : ViewModel() {

    private val TAG = ProfileViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    private val _profileResult =
        mutableStateOf<LiveData<Result<Response<UserResponse>>>>(liveData { })
    val profileResult: LiveData<Result<Response<UserResponse>>>
        get() = _profileResult.value

    fun getUserProfile() {
        viewModelScope.launch {
            val result = repo.getUserProfile()
            _profileResult.value = result
        }
    }

    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginUIButtonClicked -> {
//                update()
            }

        }
        validateLoginUIDataWithRules()
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )


        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status

    }

    fun logout(context: Context) {
        val userPreference = UserPreference(context)
        userPreference.removeUser()
    }

//    private fun update() {
//        loginInProgress.value = true
//        val email = loginUIState.value.email
//        val password = loginUIState.value.password
//
//        viewModelScope.launch {
//            val result = repo.login(
//                email = email,
//                password = password
//            )
//            _loginResult.value = result
//            loginInProgress.value = false
//        }
//    }

    companion object {
        fun saveToken(context: Context, loginResponse: LoginResponse) {
            val userPreference = UserPreference(context)
            val loginModel = LoginModel(
                token = loginResponse.token
            )

            userPreference.setLogin(loginModel)
        }
    }

}

