package com.c22ps099.relasiahelpseekerapp.data.api.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MissionsResponse(

	@field:SerializedName("MissionsResponse")
	val missionsResponse: List<MissionsResponseItem>
)

@Parcelize
data class MissionsResponseItem(

	@field:SerializedName("end_date")
	val endDate: String,

	@field:SerializedName("note")
	val note: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("requirement")
	val requirement: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("featured_image")
	val featuredImage: List<String>,

	@field:SerializedName("number_of_needs")
	val numberOfNeeds: String,

	@field:SerializedName("province")
	val province: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("start_date")
	val startDate: String,

	@field:SerializedName("timestamp")
	val timestamp: String,

	@field:SerializedName("volunteers")
	val volunteers: Map<String,String>
	
): Parcelable
