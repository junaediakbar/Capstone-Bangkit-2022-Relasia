package com.c22ps099.relasiahelpseekerapp.data.api.responses

import com.google.gson.annotations.SerializedName

data class FoundationsResponse(

	@field:SerializedName("data")
	val data: Foundations? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Foundations(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("volunteers")
	val volunteers: List<Any?>? = null
)
