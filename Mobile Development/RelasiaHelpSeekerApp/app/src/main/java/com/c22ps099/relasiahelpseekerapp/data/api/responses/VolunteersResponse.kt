package com.c22ps099.relasiahelpseekerapp.data.api.responses

import com.google.gson.annotations.SerializedName

data class VolunteersResponse(

	@field:SerializedName("VolunteersResponse")
	val volunteersResponse: List<VolunteersResponseItem>
)

data class VolunteersResponseItem(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("birthyear")
	val birthyear: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("missions")
	val missions: List<String>,

	@field:SerializedName("foundations")
	val foundations: List<String>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("verified")
	val verified: String
)
