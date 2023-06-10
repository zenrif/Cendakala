package com.zen.cendakala.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.responses.Survey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeViewModel(
    private val repo: SurveyRepository
) : ViewModel() {
    fun getSurveys(): Flow<PagingData<Survey>> = repo.getSurveys().cachedIn(viewModelScope)
//    val getSurveys: LiveData<PagingData<Survey>> =
//        repo.getSurveys().cachedIn(viewModelScope)
}