package com.zen.cendakala.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginModel (
    var token: String? = null
) : Parcelable

data class RegisterModel(
    var name: String,
    var email: String,
    var password: String,
    val job: String,
    val gender: String,
    val interest: Map<String, String>
)