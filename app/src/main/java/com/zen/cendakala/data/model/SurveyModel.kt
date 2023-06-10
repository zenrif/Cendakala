package com.zen.cendakala.data.model

data class SurveyModel(
    val title: String,
    val questionNum: Int? = null,
    val quota: Int,
    val reward: Long,
    val category1: String,
    val category2: String,
    val description: String,
    val questions: Map<String, Question>? = null
)


data class Question(
    val type: String,
    val choiceNum: Int? = null,
    val question: String,
    val choices: Map<String, String>? = null
)