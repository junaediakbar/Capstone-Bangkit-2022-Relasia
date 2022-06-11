package com.c22ps099.relasiahelpseekerapp.data.api.responses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class VolunteersByMissionResponse(

	@field:SerializedName("lenght")
	val lenght: Int? = null,

	@field:SerializedName("volunteers")
	val volunteers: List<VolunteersItem?>? = null
)


@Entity(tableName = "volunteers")
@Parcelize
data class VolunteersItem(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("birthyear")
	val birthyear: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("missions")
	val missions: List<String?>? = null,

	@field:SerializedName("foundations")
	val foundations: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("verified")
	val verified: String? = null,

	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("status")
	var status: String? = null
)
	:Parcelable
