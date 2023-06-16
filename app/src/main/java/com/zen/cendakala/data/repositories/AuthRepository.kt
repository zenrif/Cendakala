package com.zen.cendakala.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.zen.cendakala.data.Result
import com.zen.cendakala.data.model.LoginData
import com.zen.cendakala.data.model.RegisterModel
import com.zen.cendakala.data.responses.LoginResponse
import com.zen.cendakala.data.responses.RegisterResponse
import com.zen.cendakala.data.source.local.UserPreference
import com.zen.cendakala.data.source.remote.ApiServices
import retrofit2.Response

class AuthRepository(
    private val pref: UserPreference, private val apiService: ApiServices,
) {
    fun login(
        email: String,
        password: String,
    ): LiveData<Result<Response<LoginResponse>>> = liveData {
        emit(Result.Loading)
        try {
            val loginData = LoginData(
                email = email,
                password = password
            )
            val response = apiService.login(
                loginData = loginData
            )
            if (response.isSuccessful) {
                emit(Result.Success(response))
            } else {
                emit(Result.Error(response))
            }
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        job: String,
        gender: String,
        interest: Map<String, String>,
    ): LiveData<Result<Response<RegisterResponse>>> = liveData {
        emit(Result.Loading)
        try {
            val registerModel = RegisterModel(
                name = name,
                email = email,
                password = password,
                job = job,
                gender = gender,
                interest = interest
            )
            val response = apiService.register(
                registerModel = registerModel
            )
            if (response.isSuccessful) {
                emit(Result.Success(response))
            } else {
                emit(Result.Error(response))
            }
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            preferences: UserPreference,
            apiService: ApiServices,
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(preferences, apiService)
            }.also { instance = it }
    }
}