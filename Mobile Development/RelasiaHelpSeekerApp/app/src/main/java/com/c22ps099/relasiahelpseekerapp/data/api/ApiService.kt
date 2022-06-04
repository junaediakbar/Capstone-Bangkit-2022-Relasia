package com.c22ps099.relasiahelpseekerapp.data.api

import com.c22ps099.relasiahelpseekerapp.data.api.responses.*
import com.c22ps099.relasiahelpseekerapp.model.Helpseeker
import com.c22ps099.relasiahelpseekerapp.model.Mission
import com.squareup.okhttp.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("mission")
    fun getAllMissions(
        @Query("helpseeker") id:String,
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

    @GET("mission/{id}")
    fun getVoluntersByMission(
        @Path("id") id: String,
        @Query("data") volunteers: String,
    ): Call<VolunteersByMissionResponse>

    @PUT("mission/{mission.id}")
    fun changeVolunteerStatus(
        @Path("mission.id") missioId :String,
        @Body jsonObject:JSONObject
    ): Call<GeneralResponse>

    @GET("foundation")
    fun getFoundation(): Call<FoundationsResponse>

    @DELETE("mission")
    fun deleteMission(
        @Body body: RequestBody
    ): Call<FoundationsResponse>
}