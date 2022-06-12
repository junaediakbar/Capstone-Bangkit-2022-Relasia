package com.c22ps099.relasiahelpseekerapp.data.api.responses

import android.os.Parcelable
import com.c22ps099.relasiahelpseekerapp.model.UserIdStatus
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FoundationsResponse(

	@field:SerializedName("data")
	val data: List<Foundation?>? = null,

	@field:SerializedName("length")
	val length: Int? = null
)

@Parcelize
data class Foundation(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

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

):Parcelable
