package com.zen.cendakala.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("code")
    val code: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("token")
    val token: String,
) : Parcelable