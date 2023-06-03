package com.zen.cendakala.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.zen.cendakala.data.Result
import com.zen.cendakala.data.responses.GeneralResponse
import com.zen.cendakala.data.responses.LoginResponse
import com.zen.cendakala.data.responses.RegisterResponse
import com.zen.cendakala.data.responses.Survey
import com.zen.cendakala.data.responses.SurveyResponse
import com.zen.cendakala.data.source.SurveyPagingSource
import com.zen.cendakala.data.source.SurveyRemoteMediator
import com.zen.cendakala.data.source.local.UserPreference
import com.zen.cendakala.data.source.remote.ApiServices
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.Objects

class SurveyRepository (
    private val pref: UserPreference, private val apiService: ApiServices
) {
//    fun getListStories(): LiveData<PagingData<Survey>> {
//        @OptIn(ExperimentalPagingApi::class)
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5
//            ),
//            remoteMediator = SurveyRemoteMediator(pref, apiService),
//            pagingSourceFactory = {
//                SurveyPagingSource(pref, apiService)
//            }
//        ).liveData
//    }

    fun login(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(
                email,
                password
            )
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        birthday: String,
        job: String,
        gender: String,
        interest: Objects
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(
                name,
                email,
                password,
                birthday,
                job,
                gender,
                interest
            )
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addSurvey(
        imageFile: MultipartBody.Part,
        desc: RequestBody,
        lat: Double,
        lon: Double
    ): LiveData<Result<GeneralResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.addStory(
                token = "Bearer ${pref.getUser().token}",
                file = imageFile,
                description = desc,
                lat = lat,
                lon = lon
            )
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun stories(): LiveData<Result<SurveyResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.survey(
                token = "Bearer ${pref.getUser().token}",
                page = 1,
                size = 100,
                location = 1
            )
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: SurveyRepository? = null
        fun getInstance(
            preferences: UserPreference,
            apiService: ApiServices
        ): SurveyRepository =
            instance ?: synchronized(this) {
                instance ?: SurveyRepository(preferences, apiService)
            }.also { instance = it }
    }
}