package com.c22ps099.relasiahelpseekerapp.data.api.responses

data class AddNewHelpSeekerResponse(
	val data: Data? = null,
	val message: String? = null
)

data class Data(
	val city: String? = null,
	val phone: String? = null,
	val missions: List<MissionItem>? = null,
	val name: String? = null,
	val id: String? = null
)

