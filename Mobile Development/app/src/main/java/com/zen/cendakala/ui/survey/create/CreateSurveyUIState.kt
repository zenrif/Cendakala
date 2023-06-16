package com.zen.cendakala.ui.survey.create

import com.zen.cendakala.data.model.Question

data class CreateSurveyUIState(
    val title: String = "",
    val questionNum: Int = 0,
    val quota: Int = 0,
    val reward: Long = 0,
    val category1: String = "",
    val category2: String = "",
    val description: String = "",
    val questions: Map<String, Question>? = null,
)
