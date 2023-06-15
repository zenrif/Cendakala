package com.zen.cendakala.ui.survey

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

class SurveyViewModel(
    private val repo: SurveyRepository
) : ViewModel() {
    private val _tokenResult = mutableStateOf<LiveData<Result<TokenResponse>>>(liveData { })
    val tokenResult: LiveData<Result<TokenResponse>>
        get() = _tokenResult.value
    fun getSurveys(): Flow<PagingData<Survey>> = repo.getAllSurveys().cachedIn(viewModelScope)
}