package com.zen.cendakala.ui.survey

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.zen.cendakala.data.Result
import com.zen.cendakala.data.model.SurveyModel
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.responses.CreateSurveyResponse
import com.zen.cendakala.data.source.local.CreateSurvey
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CreateSurveyViewModel(
    private val  surveyRepository: SurveyRepository
) : ViewModel() {
    private val TAG = CreateSurveyViewModel::class.simpleName
    private val _createSurvey = mutableStateListOf<CreateSurvey>()
    val createSurvey: List<CreateSurvey> = _createSurvey
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    private var createSurveyUIState = mutableStateOf(CreateSurveyUIState())

    private val _createSurveyResult = mutableStateOf<LiveData<Result<CreateSurveyResponse>>>(liveData { })
    val createSurveyResult: LiveData<Result<CreateSurveyResponse>>
        get() = _createSurveyResult.value

    val title = mutableStateOf("")
    val questionNum = mutableStateOf(0)
    val quota = mutableStateOf(0)
    val reward = mutableStateOf(0L)
    val category1 = mutableStateOf("")
    val category2 = mutableStateOf("")
    val description = mutableStateOf("")
    fun onEvent(event: CreateSurveyUIEvent) {
        when (event) {
            is CreateSurveyUIEvent.Done1ButtonClicked -> {
                saveSurvey()
            }
        }
        validateDataWithRules()
    }

    private fun validateDataWithRules() {
        Log.d(TAG, "{${title.value}}")
        Log.d(TAG, "{${questionNum.value}}")
        Log.d(TAG, "{${quota.value}}")
        Log.d(TAG, "{${reward.value}}")
        Log.d(TAG, "{${category1.value}}")
        Log.d(TAG, "{${category2.value}}")
        Log.d(TAG, "{${description.value}}")
    }

    private fun saveSurvey() {
        val title = title.value
        val quota = quota.value
        val reward = reward.value
        val category1 = category1.value
        val category2 = category2.value
        val description = description.value

        SurveyModel(
            title = title,
            quota = quota,
            reward = reward,
            category1 = category1,
            category2 = category2,
            description = description,
        ).also {
            executorService.execute {
                surveyRepository.saveSurvey(it)
            }
        }
    }
    private fun newSurvey() {
        val title = title.value
        val questionNum = questionNum.value
        val quota = quota.value
        val reward = reward.value
        val category1 = category1.value
        val category2 = category2.value
        val description = description.value

        viewModelScope.launch {
            val result = surveyRepository.createSurvey(
                title = title,
                questionNum = questionNum,
                quota = quota,
                reward = reward,
                category1 = category1,
                category2 = category2,
                description = description,
                questions = mutableMapOf()
            )
            _createSurveyResult.value = result
        }
    }
}