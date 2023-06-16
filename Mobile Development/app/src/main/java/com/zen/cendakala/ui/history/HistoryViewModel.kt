package com.zen.cendakala.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.responses.Response
import kotlinx.coroutines.flow.Flow

class HistoryViewModel(private val repo: SurveyRepository) : ViewModel() {
    fun getHistory(): Flow<PagingData<Response>> = repo.getHistory().cachedIn(viewModelScope)
}