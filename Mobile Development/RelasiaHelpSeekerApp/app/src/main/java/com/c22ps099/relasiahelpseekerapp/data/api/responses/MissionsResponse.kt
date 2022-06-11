package com.c22ps099.relasiahelpseekerapp.data.api.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MissionsResponse(

	@field:SerializedName("data_on_page")
	val dataOnPage: Int? = null,

	@field:SerializedName("data")
	val data: List<MissionItem?>? = null,

	@field:SerializedName("length")
	val length: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null
)

@Parcelize
data class MissionItem(

	@field:SerializedName("end_date")
	val endDate: String? = "",

	@field:SerializedName("note")
	val note: String? ="",

	@field:SerializedName("address")
	val address: String? = "",

	@field:SerializedName("city")
	val city: String? = "",

	@field:SerializedName("requirement")
	val requirement: String? = "",

	@field:SerializedName("helpseeker")
	val helpseeker: String? = "",

	@field:SerializedName("title")
	val title: String? = "",

	@field:SerializedName("featured_image")
	var featuredImage: List<String?>? = null,

	@field:SerializedName("number_of_needs")
	val numberOfNeeds: String? = "1",

	@field:SerializedName("province")
	val province: String? = "",

	@field:SerializedName("id")
	val id: String? = "",

	@field:SerializedName("category")
	val category: String? = "",

	@field:SerializedName("start_date")
	val startDate: String? = "",

	@field:SerializedName("timestamp")
	val timestamp: String? = ""
): Parcelable
