package com.zen.cendakala.data.responses

import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @field:SerializedName("status")
    val error: Boolean,

    @field:SerializedName("code")
    val code: String,

    @field:SerializedName("message")
    val message: String
)