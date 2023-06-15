package com.zen.cendakala.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("token")
    val token: String
): Parcelable

@Parcelize
data class UserResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: User
): Parcelable

@Parcelize
data class User(
    val birthday: String,
    val uid: String,
    val gender: String,
    val balance: String,
    val interest: Map<String, String>,
    val name: String,
    val history: Map<String, Int>,
    val job: String
) : Parcelable
