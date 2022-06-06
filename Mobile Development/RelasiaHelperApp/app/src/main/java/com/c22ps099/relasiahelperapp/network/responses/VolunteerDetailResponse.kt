package com.c22ps099.relasiahelperapp.network.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class AddNewVolunteerResponse(
	val data: VolunteerDetailData? = null,
	val message: String? = null
)

@Parcelize
data class VolunteerDetailData(
	val address: String? = null,
	val birthyear: String? = null,
	val gender: String? = null,
	val city: String? = null,
	val province: String? = null,
	val phone: String? = null,
	val name: String? = null,
	val id: String? = null
) : Parcelable
