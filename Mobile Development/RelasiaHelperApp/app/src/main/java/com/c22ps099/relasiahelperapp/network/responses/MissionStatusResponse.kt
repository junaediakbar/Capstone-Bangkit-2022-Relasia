package com.c22ps099.relasiahelperapp.network.responses

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionStatusResponse(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("birthyear")
	val birthyear: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("province")
	val province: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("missions")
	val missions: List<MissionsItem>,

	@field:SerializedName("foundations")
	val foundations: List<String>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("verified")
	val verified: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("picture")
	val picture: String
) : Parcelable

@Parcelize
data class VolunteersItemStatus(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("status")
	val status: String
) : Parcelable

@Entity(tableName = "mission_status")
@Parcelize
data class MissionsItem(

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

	@field:SerializedName("helpseeker")
	val helpseeker: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("volunteers")
	val volunteers: List<VolunteersItemStatus>,

	@field:SerializedName("featured_image")
	val featuredImage: List<String>? = null,

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
	val timestamp: String
) : Parcelable
