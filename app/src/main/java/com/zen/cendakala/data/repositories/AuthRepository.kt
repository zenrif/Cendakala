package com.zen.cendakala.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.zen.cendakala.data.Result
import com.zen.cendakala.data.model.Question
import com.zen.cendakala.data.model.SurveyModel
import com.zen.cendakala.data.responses.CreateSurveyResponse
import com.zen.cendakala.data.responses.GeneralResponse
import com.zen.cendakala.data.responses.LoginResponse
import com.zen.cendakala.data.responses.RegisterResponse
import com.zen.cendakala.data.responses.Survey
import com.zen.cendakala.data.responses.SurveyResponse
import com.zen.cendakala.data.source.SurveyPagingSource
import com.zen.cendakala.data.source.SurveyRemoteMediator
import com.zen.cendakala.data.source.local.CreateSurvey
import com.zen.cendakala.data.source.local.UserPreference
import com.zen.cendakala.data.source.remote.ApiServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AuthRepository (
    private val pref: UserPreference, private val apiService: ApiServices
) {
    fun login(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> = liveData {
        try {
            val response = apiService.login(
                email,
                password
            )
            if (response.status == "Failed") {
                emit(Result.Error(response))
            } else {
                emit(Result.Success(response))
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
        interest: Map<String, String>
    ): LiveData<Result<RegisterResponse>> = liveData {
        try {
            val response = apiService.register(
                name,
                email,
                password,
                job,
                gender,
                interest
            )
            if (response.status == "Failed") {
                emit(Result.Error(response))
            } else {
                emit(Result.Success(response))
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
            apiService: ApiServices
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(preferences, apiService)
            }.also { instance = it }
    }
}