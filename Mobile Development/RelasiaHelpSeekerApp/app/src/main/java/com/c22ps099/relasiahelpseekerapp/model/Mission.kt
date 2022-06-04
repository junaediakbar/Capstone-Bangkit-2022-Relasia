package com.c22ps099.relasiahelpseekerapp.model

import com.google.gson.annotations.SerializedName

data class Mission(

	@field:SerializedName("helpseeker")
	val helpseeker: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null,

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("featured_image")
	val featuredImage: List<String?>? = null,


	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("requirement")
	val requirement: String? = null,

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("number_of_needs")
	val numberOfNeeds: String? = null,


)
