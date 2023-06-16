package com.zen.cendakala.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("surveys")
    val surveys: List<Survey>,
) : Parcelable

@Parcelize
data class SurveyByIdResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("survey")
    val survey: Survey,
) : Parcelable

@Parcelize
data class Survey(
    val reward: Long,
    val category2: String,
    val surveyID: String,
    val category1: String,
    val questions: Map<String, Question>,
    val title: String,
    val createdAt: String,
    val uid: String,
    val questionNum: Int,
    val finished: Boolean,
    val quota: Int,
    val sell: Boolean,
    val price: Int,
    val description: String,
) : Parcelable

@Parcelize
data class Question(
    val question: String,
    val choiceNum: Int?,
    val type: String,
    val choices: Map<String, String>?,
) : Parcelable

@Parcelize
data class CreateSurveyResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("surveyID")
    val surveyID: String,
) : Parcelable

@Parcelize
data class HistoryResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("responses")
    val responses: List<Response>,
) : Parcelable

@Parcelize
data class Response(
    @SerializedName("reward") val reward: Int,
    @SerializedName("uid") val uid: String,
    @SerializedName("surveyID") val surveyID: String,
    @SerializedName("answers") val answers: Map<String, Answer>,
    @SerializedName("responseID") val responseID: String,
    @SerializedName("timestamp") val timestamp: Timestamp,
    @SerializedName("title") val title: String,
) : Parcelable {

    @Parcelize
    data class Answer(
        @SerializedName("answer") val answer: String,
        @SerializedName("type") val type: String,
        @SerializedName("choice") val choice: String,
    ) : Parcelable

    @Parcelize
    data class Timestamp(
        @SerializedName("_seconds") val seconds: Long,
        @SerializedName("_nanoseconds") val nanoseconds: Int,
    ) : Parcelable
}

@Parcelize
data class SurveyUserResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("surveys")
    val surveys: List<SurveyUser>,
) : Parcelable

@Parcelize
data class SurveyUser(
    val reward: Long,
    val category2: String,
    val surveyID: String,
    val category1: String,
    val sell: Boolean,
    val questions: Map<String, Question>,
    val description: String,
    val finished: Boolean,
    val title: String,
    val createdAt: String,
    val uid: String,
    val quota: Int,
    val questionNum: Int,
) : Parcelable