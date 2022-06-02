package com.c22ps099.relasiahelperapp.network.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VolunteerResponse(

	@field:SerializedName("data")
	val data: List<VolunteerDataItem>,

	@field:SerializedName("length")
	val length: Int
) : Parcelable

@Parcelize
data class VolunteerDataItem(

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
) : Parcelable
