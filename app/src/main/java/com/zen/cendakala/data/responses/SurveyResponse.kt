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
     val surveys: List<Survey>
 ) : Parcelable

 @Parcelize
 data class Survey(
     val reward: Int,
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
     val price: Int
 ) : Parcelable

 @Parcelize
 data class Question(
     val question: String,
     val choiceNum: Int?,
     val type: String,
     val choices: Map<String, String>?
 ) : Parcelable

 @Parcelize
 data class CreateSurveyResponse(
     @field:SerializedName("status")
     val status: String,

     @field:SerializedName("message")
     val message: String,

     @field:SerializedName("surveyID")
     val surveyID : String,
 ): Parcelable
