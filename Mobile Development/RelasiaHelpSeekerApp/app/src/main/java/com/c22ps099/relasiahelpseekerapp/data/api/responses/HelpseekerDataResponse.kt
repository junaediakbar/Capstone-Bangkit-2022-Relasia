package com.c22ps099.relasiahelpseekerapp.data.api.responses

import com.google.gson.annotations.SerializedName

data class HelpseekerDataResponse(

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null
)
