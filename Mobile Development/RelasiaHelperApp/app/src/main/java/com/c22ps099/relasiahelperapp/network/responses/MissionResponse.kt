package com.c22ps099.relasiahelperapp.network.responses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionResponse(

	@field:SerializedName("data_on_page")
	val dataOnPage: String,

	@field:SerializedName("data")
	val data: List<MissionDataItem>,

	@field:SerializedName("length")
	val length: String,

	@field:SerializedName("page")
	val page: String
) : Parcelable

@Entity(tableName = "mission")
@Parcelize
data class MissionDataItem(

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

	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("start_date")
	val startDate: String,

	@field:SerializedName("timestamp")
	val timestamp: String,

	@field:SerializedName("helpseeker")
	val helpseeker: String
) : Parcelable
