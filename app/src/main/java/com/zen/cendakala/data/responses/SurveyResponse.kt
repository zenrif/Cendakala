 package com.zen.cendakala.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listSurvey")
    val listSurvey: ArrayList<Survey>
): Parcelable

@Parcelize
data class Survey(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("lat")
    val lat: Double,

    @field:SerializedName("lon")
    val lon: Double,
): Parcelable

 @Parcelize
 data class CreateSurveyResponse(
     @field:SerializedName("status")
     val status: String,

     @field:SerializedName("message")
     val message: String,

     @field:SerializedName("surveyID")
     val surveyID : String,
 ): Parcelable
