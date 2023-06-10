package com.zen.cendakala.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class SurveyModel(
    val id: Int,
    val title: String,
    val questionNum: Int,
    val quota: Int,
    val reward: Int,
    val category1: String,
    val category2: String,
    val description: String,
    val questions: Map<String, Question>
)


data class Question(
    val type: String,
    val choiceNum: Int? = null,
    val question: String,
    val choices: Map<String, String>? = null
)