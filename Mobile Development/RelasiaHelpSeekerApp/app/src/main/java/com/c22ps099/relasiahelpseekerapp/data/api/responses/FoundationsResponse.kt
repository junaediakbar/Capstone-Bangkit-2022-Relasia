package com.c22ps099.relasiahelpseekerapp.data.api.responses

import com.google.gson.annotations.SerializedName

data class FoundationsResponse(

	@field:SerializedName("data")
	val data: List<Foundation?>? = null,

	@field:SerializedName("length")
	val length: Int? = null
)


data class Foundation(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

)
