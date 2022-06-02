package com.c22ps099.relasiahelperapp.network.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HelpseekerResponse(

	@field:SerializedName("data")
	val data: List<HelpseekerDataItem>,

	@field:SerializedName("length")
	val length: Int
) : Parcelable

@Parcelize
data class HelpseekerDataItem(

	@field:SerializedName("mission")
	val mission: List<String>,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String
) : Parcelable
