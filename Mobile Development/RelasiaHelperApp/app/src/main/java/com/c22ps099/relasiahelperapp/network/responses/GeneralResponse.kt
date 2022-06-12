package com.c22ps099.relasiahelperapp.network.responses

import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @field:SerializedName("message")
    val message: String
)