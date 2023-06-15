package com.zen.cendakala.ui.auth.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.zen.cendakala.data.Result
import com.zen.cendakala.data.model.LoginModel
import com.zen.cendakala.data.repositories.AuthRepository
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.responses.LoginResponse
import com.zen.cendakala.data.rules.Validator
import com.zen.cendakala.data.source.local.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel (
    private val repo: AuthRepository
) : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    private val _loginResult = mutableStateOf<LiveData<Result<Response<LoginResponse>>>>(liveData { })
    val loginResult: LiveData<Result<Response<LoginResponse>>>
        get() = _loginResult.value


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
                login()
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

    private fun login() {
        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password

        viewModelScope.launch {
            val result = repo.login(
                email = email,
                password = password
            )
            _loginResult.value = result
            loginInProgress.value = false
        }
    }

    companion object {
        fun saveToken(context: Context, token: String) {
            val userPreference = UserPreference(context)
            val loginModel = LoginModel(
                token = token
            )

            userPreference.setLogin(loginModel)
        }
    }

}

