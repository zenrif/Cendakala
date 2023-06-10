package com.zen.cendakala.ui.survey

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zen.cendakala.data.model.SurveyModel
import com.zen.cendakala.data.repositories.CreateSurveyRepository
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.source.local.CreateSurvey
import com.zen.cendakala.data.source.local.CreateSurveyDao
import com.zen.cendakala.data.source.local.CreateSurveyRoomDatabase
import com.zen.cendakala.ui.auth.register.RegisterUIEvent
import com.zen.cendakala.ui.auth.register.RegisterUIState
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CreateSurveyViewModel(
    application: Application,
    private val  surveyRepository: SurveyRepository
) : ViewModel() {
    private val _createSurvey = mutableStateListOf<CreateSurvey>()
    val createSurvey: List<CreateSurvey> = _createSurvey

    private val createSurveyDao: CreateSurveyDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    private var createSurveyUIState = mutableStateOf(CreateSurveyUIState())

    fun onEvent(event: CreateSurveyUIEvent) {
        when (event) {
            is CreateSurveyUIEvent.Done1ButtonClicked -> {
                newSurvey()
            }
        }
    }

    init {
        val db = CreateSurveyRoomDatabase.getDatabase(application)
        createSurveyDao = db.createSurveyDao()
        var createSurveyRepository = CreateSurveyRepository(createSurveyDao)
    }

    fun addCreateSurvey(createSurvey: CreateSurvey){
        viewModelScope.launch {
            createSurveyDao.insert(createSurvey)
        }
    }

    fun updateCreateSurvey(createSurvey: CreateSurvey) {
        viewModelScope.launch {
            createSurveyDao.update(createSurvey)
        }
    }

    fun deleteCreateSurvey(id: Int) {
        viewModelScope.launch {
            createSurveyDao.deleteById(id)
        }
    }

    private fun newSurvey() {
        val title = createSurveyUIState.value.title
        val questionNum = createSurveyUIState.value.questionNum
        val quota = createSurveyUIState.value.quota
        val reward = createSurveyUIState.value.reward
        val category1 = createSurveyUIState.value.category1
        val category2 = createSurveyUIState.value.category2
        val description = createSurveyUIState.value.description

        val newSurvey = CreateSurvey(
            title = "",
            questionNum = 0,
            quota = 0,
            reward = 0,
            category1 = "",
            category2 = "",
            description = "",
            questions = mutableMapOf()
        )
        addCreateSurvey(newSurvey)
    }
}