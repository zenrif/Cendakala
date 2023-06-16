package com.zen.cendakala.ui.home

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zen.cendakala.data.Result
import com.zen.cendakala.data.model.LoginModel
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.responses.Survey
import com.zen.cendakala.data.responses.TokenResponse
import com.zen.cendakala.data.source.local.UserPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(
    private val repo: SurveyRepository,
) : ViewModel() {
    private val _tokenResult =
        mutableStateOf<LiveData<Result<Response<TokenResponse>>>>(liveData { })
    val tokenResult: LiveData<Result<Response<TokenResponse>>>
        get() = _tokenResult.value

    fun getSurveys(): Flow<PagingData<Survey>> = repo.getSurveys().cachedIn(viewModelScope)

    fun recomSurveys(): Flow<PagingData<Survey>> = repo.recomSurveys().cachedIn(viewModelScope)
    fun cekToken() {
        viewModelScope.launch {
            val result = repo.cekToken()
            _tokenResult.value = result
        }
    }

    companion object {
        fun saveToken(context: Context, token: String) {
            val userPreference = UserPreference(context)
            val tokenModel = LoginModel(
                token = token
            )

            userPreference.setLogin(tokenModel)
        }
    }

}