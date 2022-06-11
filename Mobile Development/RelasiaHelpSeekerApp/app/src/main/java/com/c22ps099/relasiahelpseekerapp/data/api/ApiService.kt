package com.c22ps099.relasiahelpseekerapp.data.api

import com.c22ps099.relasiahelpseekerapp.data.api.responses.*
import com.c22ps099.relasiahelpseekerapp.model.UserIdStatus
import com.c22ps099.relasiahelpseekerapp.model.Helpseeker
import com.c22ps099.relasiahelpseekerapp.model.Mission
import com.c22ps099.relasiahelpseekerapp.model.MissionId
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

    @Headers("Content-Type: application/json")
    @PUT("helpseeker")
    fun updateHelpseeker(
        @Body helpseeker:Helpseeker
    ): Call<AddNewHelpSeekerResponse>

    @GET("mission/{id}")
    fun getVoluntersByMission(
        @Path("id") id: String,
        @Query("data") volunteers: String,
    ): Call<VolunteersByMissionResponse>

    @Headers("Content-Type: application/json")
    @PUT("mission/{missionId}")
    fun changeVolunteerStatus(
        @Path("missionId") missionId :String,
        @Body userIdStatus: UserIdStatus
    ): Call<GeneralResponse>

    @GET("foundation")
    fun getFoundation(): Call<FoundationsResponse>

    //https://stackoverflow.com/a/41629528
    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "/mission",hasBody = true)
    fun deleteMyMission(
        @Body missionId: MissionId
    ): Call<GeneralResponse>

    @Headers("Content-Type: application/json")
    @GET("helpseeker/{helpseekerId}")
    fun getSpecificHelpseeker(
        @Path("helpseekerId") missionId :String,
    ): Call<HelpseekerDataResponse>


}