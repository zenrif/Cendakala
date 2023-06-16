package com.zen.cendakala.ui.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.responses.SurveyUser
import kotlinx.coroutines.flow.Flow

class SurveyViewModel(private val repo: SurveyRepository) : ViewModel() {
    fun getSurveyUser(): Flow<PagingData<SurveyUser>> =
        repo.getSurveyUser().cachedIn(viewModelScope)
}