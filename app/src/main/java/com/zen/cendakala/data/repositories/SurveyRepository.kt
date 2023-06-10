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

class SurveyRepository (
    private val pref: UserPreference, private val apiService: ApiServices
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getSurveys() = Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            remoteMediator = SurveyRemoteMediator(pref, apiService),
            pagingSourceFactory = {
                SurveyPagingSource(pref, apiService)
            }
        ).flow

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
         reward: Long,
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

//    fun stories(): LiveData<Result<SurveyResponse>> = liveData {
//        try {
//            val response = apiService.purchaseable(
//                authtoken = "${pref.getUser().token}",
//            )
//            if (response.status == "Failed") {
//                emit(Result.Error(response.message))
//            } else {
//                emit(Result.Success(response))
//            }
//        } catch (e: Exception) {
//            emit(Result.Error(e.message.toString()))
//        }
//    }

    fun saveSurvey(it: SurveyModel) {
        CoroutineScope(Dispatchers.IO).launch {
            pref.saveSurvey(it)
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