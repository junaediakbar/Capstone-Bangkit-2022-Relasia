package com.c22ps099.relasiahelperapp.data

import com.google.gson.annotations.SerializedName

data class Volunteer(
    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("birthyear")
    val birthyear: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("province")
    val province: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null
)
