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
//    private val _uiState: MutableStateFlow<UiState<SurveyModel>> =
//        MutableStateFlow(UiState.Loading)
//
//    val uiState: StateFlow<UiState<SurveyModel>> get() = _uiState
    private val _detailResult = mutableStateOf<LiveData<Result<SurveyByIdResponse>>>(liveData { })
    val detailResult: LiveData<Result<SurveyByIdResponse>>
        get() = _detailResult.value
    fun getById(surveyID: String) {
        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            _uiState.value = UiState.Success(repo.getById(surveyID = surveyID))
            val result = repo.getById(surveyID = surveyID)
            _detailResult.value = result
        }
    }

//    fun getDataById(surveyID: String): SurveyModel {
//        return repo.getById(surveyID = surveyID)
//    }
}