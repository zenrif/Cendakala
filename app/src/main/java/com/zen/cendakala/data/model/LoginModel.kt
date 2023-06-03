package com.zen.cendakala.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginModel (
    var token: String? = null
) : Parcelable