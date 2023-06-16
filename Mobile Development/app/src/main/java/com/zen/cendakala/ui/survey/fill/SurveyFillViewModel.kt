package com.zen.cendakala.ui.survey.fill

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.zen.cendakala.data.Result
import com.zen.cendakala.data.model.Answer
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.responses.GeneralResponse
import com.zen.cendakala.data.responses.SurveyByIdResponse
import kotlinx.coroutines.launch

class SurveyFillViewModel(private val repo: SurveyRepository) : ViewModel() {
    private val _detailResult = mutableStateOf<LiveData<Result<SurveyByIdResponse>>>(liveData { })
    val detailResult: LiveData<Result<SurveyByIdResponse>>
        get() = _detailResult.value
    private val _answerResult = mutableStateOf<LiveData<Result<GeneralResponse>>>(liveData { })
    val answerResult: LiveData<Result<GeneralResponse>>
        get() = _answerResult.value

    var answerState by mutableStateOf(Answer("", null, ""))

    fun updateAnswer(answer: Answer) {
        answerState = answer
    }

    private val _answersState = mutableStateOf<MutableMap<String, Answer>>(mutableMapOf())
    var answersState: MutableMap<String, Answer>
        get() = _answersState.value
        set(value) {
            _answersState.value = value
        }

    fun saveAnswer(questionNumber: String, answer: Answer) {
        answersState = (answersState + (questionNumber to answer)) as MutableMap<String, Answer>
        Log.d("SurveyFillViewModel", "saveAnswer: $answersState")
        Log.d("SurveyFillViewModel", "ANSWERS: $answer")
    }

    fun getById(surveyID: String) {
        viewModelScope.launch {
            val result = repo.getQuestionsById(surveyID = surveyID)
            _detailResult.value = result
        }
    }

    fun submitAnswer(surveyID: String, reward: Long) {
        viewModelScope.launch {
            val result =
                repo.submitAnswer(answers = answersState, surveyID = surveyID, reward = reward)
            _answerResult.value = result
        }
    }
}