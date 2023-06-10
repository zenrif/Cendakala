package com.zen.cendakala.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.zen.cendakala.data.Result
import com.zen.cendakala.data.model.Question
import com.zen.cendakala.data.responses.CreateSurveyResponse
import com.zen.cendakala.data.responses.GeneralResponse
import com.zen.cendakala.data.responses.LoginResponse
import com.zen.cendakala.data.responses.RegisterResponse
import com.zen.cendakala.data.responses.SurveyResponse
import com.zen.cendakala.data.source.local.CreateSurvey
import com.zen.cendakala.data.source.local.UserPreference
import com.zen.cendakala.data.source.remote.ApiServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

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
        try {
            val response = apiService.login(
                email,
                password
            )
            if (response.status == "Failed") {
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
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun createSurvey(
         title: String,
         questionNum: Int,
         quota: Int,
         reward: Int,
         category1: String,
         category2: String,
         description: String,
         questions: Map<String, Question>
    ): LiveData<Result<CreateSurveyResponse>> = liveData {
        try {
            val response = apiService.createSurvey(
                authtoken = "Bearer ${pref.getUser().token}",
                title = title,
                questionNum = questionNum,
                quota = quota,
                reward = reward,
                category1 = category1,
                category2 = category2,
                description = description,
                questions = questions
            )
            if (response.status == "Failed") {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun stories(): LiveData<Result<SurveyResponse>> = liveData {
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