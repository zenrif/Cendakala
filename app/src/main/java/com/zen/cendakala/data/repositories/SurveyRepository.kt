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
import com.zen.cendakala.data.model.Answer
import com.zen.cendakala.data.model.AnswerModel
import com.zen.cendakala.data.model.Question
import com.zen.cendakala.data.model.SurveyModel
import com.zen.cendakala.data.responses.CreateSurveyResponse
import com.zen.cendakala.data.responses.GeneralResponse
import com.zen.cendakala.data.responses.LoginResponse
import com.zen.cendakala.data.responses.RegisterResponse
import com.zen.cendakala.data.responses.Survey
import com.zen.cendakala.data.responses.SurveyByIdResponse
import com.zen.cendakala.data.responses.SurveyResponse
import com.zen.cendakala.data.responses.TokenResponse
import com.zen.cendakala.data.responses.UserResponse
import com.zen.cendakala.data.source.HistoryPagingSource
import com.zen.cendakala.data.source.HistoryRemoteMediator
import com.zen.cendakala.data.source.SurveyAllPagingSource
import com.zen.cendakala.data.source.SurveyAllRemoteMediator
import com.zen.cendakala.data.source.SurveyPagingSource
import com.zen.cendakala.data.source.SurveyRecomPagingSource
import com.zen.cendakala.data.source.SurveyRecomRemoteMediator
import com.zen.cendakala.data.source.SurveyRemoteMediator
import com.zen.cendakala.data.source.local.CreateSurvey
import com.zen.cendakala.data.source.local.UserPreference
import com.zen.cendakala.data.source.remote.ApiServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

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

    @OptIn(ExperimentalPagingApi::class)
    fun recomSurveys() = Pager(
            config = PagingConfig(
                pageSize = 2,
            ),
            remoteMediator = SurveyRecomRemoteMediator(pref, apiService),
            pagingSourceFactory = {
                SurveyRecomPagingSource(pref, apiService)
            }
        ).flow

    @OptIn(ExperimentalPagingApi::class)
    fun getAllSurveys() = Pager(
        config = PagingConfig(
            pageSize = 5,
        ),
        remoteMediator = SurveyAllRemoteMediator(pref, apiService),
        pagingSourceFactory = {
            SurveyAllPagingSource(pref, apiService)
        }
    ).flow
    @OptIn(ExperimentalPagingApi::class)
    fun getHistory() = Pager(
        config = PagingConfig(
            pageSize = 5,
        ),
        remoteMediator = HistoryRemoteMediator(pref, apiService),
        pagingSourceFactory = {
            HistoryPagingSource(pref, apiService)
        }
    ).flow
    fun cekToken(): LiveData<Result<Response<TokenResponse>>> = liveData {
        try {
            val response = apiService.cekToken(
                authtoken = "${pref.getUser().token}"
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
                emit(Result.Error(response))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getById(surveyID: String): LiveData<Result<SurveyByIdResponse>> = liveData {
        try {
            val response = apiService.surveyById(
                authtoken = "${pref.getUser().token}",
                surveyID = surveyID
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
    fun getQuestionsById(surveyID: String): LiveData<Result<SurveyByIdResponse>> = liveData {
        try {
            val response = apiService.surveyById(
                authtoken = "${pref.getUser().token}",
                surveyID = surveyID
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

    fun submitAnswer(
        answers: Map<String, Answer>,
        surveyID: String,
        reward: Long
    ): LiveData<Result<GeneralResponse>> = liveData {
        try {
            val answer = AnswerModel(
                answers = answers,
                reward = reward,
                surveyID = surveyID
            )
            val response = apiService.submitAnswer(
                authtoken = "${pref.getUser().token}",
                answer = answer
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

    fun getUserProfile(): LiveData<Result<Response<UserResponse>>> = liveData {
        try {
            val response = apiService.getUserProfile(
                authtoken = "${pref.getUser().token}"
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