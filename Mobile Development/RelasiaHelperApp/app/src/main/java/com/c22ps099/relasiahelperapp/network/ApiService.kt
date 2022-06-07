package com.c22ps099.relasiahelperapp.network

import com.c22ps099.relasiahelperapp.data.Mission
import com.c22ps099.relasiahelperapp.data.Volunteer
import com.c22ps099.relasiahelperapp.network.responses.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("mission")
    suspend fun getAllMissions(
        @Query("page") page: Int = 1,
        @Query("paginate") paginate: Int = 5
    ): MissionResponse

    @Headers("Content-Type: application/json")
    @GET("mission")
    suspend fun getMissionsStatus(
        @Query("volunteer") volunteer: String,
        @Query("page") page: Int = 1,
        @Query("paginate") paginate: Int = 5
    ): MissionResponse

    @Headers("Content-Type: application/json")
    @GET("mission")
    suspend fun searchMission(
        @Query("title") title: String,
        @Query("page") page: Int = 1,
        @Query("paginate") paginate: Int = 5
    ): MissionResponse

    @Headers("Content-Type: application/json")
    @PUT("volunteer/{volunteerId}/mission")
    fun applyToMission(
        @Path("volunteerId") volunteerId: String,
        @Body mission: Mission
    ): Call<GeneralResponse>

    @Headers("Content-Type: application/json")
    @GET("mission/{missionId}")
    fun getMissionDetail(
        @Path("missionId") missionId: String
    ): Call<MissionDetailResponse>

    @Headers("Content-Type: application/json")
    @POST("volunteer")
    fun addVolunteer(
        @Body volunteer: Volunteer
    ): Call<AddNewVolunteerResponse>
}