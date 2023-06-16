package com.zen.cendakala.ui.survey.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.zen.cendakala.data.Result
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.responses.SurveyByIdResponse
import kotlinx.coroutines.launch

class SurveyDetailViewModel(private val repo: SurveyRepository) : ViewModel() {
    private val _detailResult = mutableStateOf<LiveData<Result<SurveyByIdResponse>>>(liveData { })
    val detailResult: LiveData<Result<SurveyByIdResponse>>
        get() = _detailResult.value

    fun getById(surveyID: String) {
        viewModelScope.launch {
            val result = repo.getById(surveyID = surveyID)
            _detailResult.value = result
        }
    }
}