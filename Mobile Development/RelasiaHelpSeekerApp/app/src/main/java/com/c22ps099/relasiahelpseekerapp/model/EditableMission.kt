package com.c22ps099.relasiahelpseekerapp.model

import com.google.gson.annotations.SerializedName

data class EditableMission(

	@field:SerializedName("number_of_needs")
	val numberOfNeeds: String? = null,

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("requirement")
	val requirement: String? = null
)
