package com.zen.cendakala.ui.auth.register

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.zen.cendakala.data.Result
import com.zen.cendakala.data.model.LoginModel
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.responses.RegisterResponse
import com.zen.cendakala.data.rules.Validator
import com.zen.cendakala.data.source.local.UserPreference
import kotlinx.coroutines.launch


class RegisterViewModel (
    private val surveyRepository: SurveyRepository
) : ViewModel() {

    private val TAG = RegisterViewModel::class.simpleName

    var registerUIState = mutableStateOf(RegisterUIState())

    var allValidationsPassed = mutableStateOf(false)

    private val _registerResult = mutableStateOf<LiveData<Result<RegisterResponse>>>(liveData { })
    val registerResult: LiveData<Result<RegisterResponse>>
        get() = _registerResult.value

    fun onEvent(event: RegisterUIEvent) {
        when (event) {
            is RegisterUIEvent.EmailChanged -> {
                registerUIState.value = registerUIState.value.copy(
                    email = event.email
                )
            }
            is RegisterUIEvent.PasswordChanged -> {
                registerUIState.value = registerUIState.value.copy(
                    password = event.password
                )
            }
            is RegisterUIEvent.NameChanged -> {
                registerUIState.value = registerUIState.value.copy(
                    name = event.name
                )
            }
            is RegisterUIEvent.GenderChanged -> {
                registerUIState.value = registerUIState.value.copy(
                    gender = event.gender
                )
            }
            is RegisterUIEvent.JobChanged -> {
                registerUIState.value = registerUIState.value.copy(
                    job = event.job
                )
            }
            is RegisterUIEvent.InterestChanged -> {
                registerUIState.value = registerUIState.value.copy(
                    interest = event.interest
                )
            }
            is RegisterUIEvent.RegisterButtonClicked -> {
                register()
            }
        }
        validateDataWithRules()
    }
    private fun validateDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = registerUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = registerUIState.value.password
        )

        val nameResult = Validator.validateName(
            name = registerUIState.value.name
        )

        val genderResult = Validator.validateGender(
            gender = registerUIState.value.gender
        )

        val jobResult = Validator.validateJob(
            job = registerUIState.value.job
        )

        val interestResult = Validator.validateInterest(
            interest = registerUIState.value.interest
        )


        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "${registerUIState.value.email}")
        Log.d(TAG, "passwordResult= $passwordResult")
        Log.d(TAG, "nameResult= $nameResult")
        Log.d(TAG, "genderResult= $genderResult")
        Log.d(TAG, "jobResult= $jobResult")
        Log.d(TAG, "{${registerUIState.value.interest}}")

        registerUIState.value = registerUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            nameError = nameResult.status,
            genderError = genderResult.status,
            jobError = jobResult.status,
            interestError = interestResult.status
        )


        allValidationsPassed.value =
            emailResult.status && passwordResult.status && nameResult.status
                    && genderResult.status && jobResult.status
    }

    private fun register(){
        val email = registerUIState.value.email
        val password = registerUIState.value.password
        val name = registerUIState.value.name
        val gender = registerUIState.value.gender
        val job = registerUIState.value.job
        val interests = registerUIState.value.interest

        val interestsObject = interests.associateBy { it.id.toString() }.mapValues { it.value.text }

        viewModelScope.launch {
            val result = surveyRepository.register(
                email = email,
                password = password,
                name = name,
                job = job,
                gender = gender,
                interest = interestsObject
            )
            _registerResult.value = result
        }
    }

    companion object {
        fun saveToken(context: Context, registerResponse: RegisterResponse) {
            val userPreference = UserPreference(context)
            val loginModel = LoginModel(
                token = registerResponse.token
            )

            userPreference.setLogin(loginModel)
        }
    }
}
