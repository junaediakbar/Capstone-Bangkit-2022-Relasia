package com.c22ps099.relasiahelperapp.network.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoundationResponse(

	@field:SerializedName("data")
	val data: List<FoundationDataItem>,

	@field:SerializedName("length")
	val length: Int
) : Parcelable

@Parcelize
data class FoundationDataItem(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("volunteers")
	val volunteers: VolunteersGroup,

	@field:SerializedName("id")
	val id: String
) : Parcelable

@Parcelize
data class VolunteersGroup(
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("status")
	val status: String,
) : Parcelable
