package com.c22ps099.relasiahelpseekerapp.data.api

import com.c22ps099.relasiahelpseekerapp.data.api.responses.AddNewHelpSeekerResponse
import com.c22ps099.relasiahelpseekerapp.data.api.responses.GeneralResponse
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionsResponse
import com.c22ps099.relasiahelpseekerapp.model.Helpseeker
import com.c22ps099.relasiahelpseekerapp.model.Mission
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("mission")
    fun getAllMissions(
    ): Call<MissionsResponse>

    @Headers("Content-Type: application/json")
    @POST("mission")
    fun addMission(
        @Body mission: Mission
    ): Call<GeneralResponse>

    @Headers("Content-Type: application/json")
    @POST("helpseeker")
    fun addHelpseeker(
        @Body helpseeker:Helpseeker
    ): Call<AddNewHelpSeekerResponse>


}