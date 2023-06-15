package com.zen.cendakala.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GeneralResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("code")
    val code: String,

    @field:SerializedName("message")
    val message: String
)

@Parcelize
data class TokenResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("code")
    val code: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("newToken")
        val newToken: newToken
) : Parcelable

@Parcelize
data class newToken(
    @field:SerializedName("token")
    val token: String
) : Parcelable

