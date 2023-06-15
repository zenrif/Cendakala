package com.zen.cendakala.ui.history

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zen.cendakala.data.Result
import com.zen.cendakala.data.model.Answer
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.responses.GeneralResponse
import com.zen.cendakala.data.responses.Survey
import com.zen.cendakala.data.responses.SurveyByIdResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HistoryViewModel(private val repo: SurveyRepository) : ViewModel() {
    fun getHistory(): Flow<PagingData<Survey>> = repo.getHistory().cachedIn(viewModelScope)
}