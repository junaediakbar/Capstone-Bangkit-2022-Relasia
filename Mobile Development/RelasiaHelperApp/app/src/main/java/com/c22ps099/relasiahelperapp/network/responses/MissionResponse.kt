package com.c22ps099.relasiahelperapp.network.responses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.c22ps099.relasiahelperapp.utils.VolunteerTypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionResponse(

	@field:SerializedName("data_on_page")
	val dataOnPage: Int,

	@field:SerializedName("data")
	val data: List<MissionDataItem>,

	@field:SerializedName("length")
	val length: Int,

	@field:SerializedName("page")
	val page: Int
) : Parcelable

@Entity(tableName = "mission")
@TypeConverters(VolunteerTypeConverters::class)
@Parcelize
data class MissionDataItem(

	@field:SerializedName("end_date")
	val endDate: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("featured_image")
	var featuredImage: List<String>? = null,

	@field:SerializedName("helpseeker")
	val helpseeker: String,

	@field:SerializedName("number_of_needs")
	val numberOfNeeds: String,

	@field:SerializedName("note")
	val note: String,

	@field:SerializedName("requirement")
	val requirement: String,

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

	@field:SerializedName("volunteers")
	val volunteers: List<VolunteersItemStatus> = listOf(),
) : Parcelable

@Parcelize
data class VolunteersItemStatus(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("status")
	val status: String
) : Parcelable
