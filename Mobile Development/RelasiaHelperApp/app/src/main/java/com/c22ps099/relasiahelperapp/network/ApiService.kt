package com.c22ps099.relasiahelperapp.network

import com.c22ps099.relasiahelperapp.data.Foundation
import com.c22ps099.relasiahelperapp.data.Mission
import com.c22ps099.relasiahelperapp.data.Volunteer
import com.c22ps099.relasiahelperapp.network.responses.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("recommend/{volunteerId}")
    suspend fun getAllMissions(
        @Path("volunteerId") volunteerId: String,
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
    fun filterMissionsAccepted(
        @Query("volunteer") volunteer: String,
        @Query("status") status: String = "accepted",
        @Query("page") page: Int = 1,
        @Query("paginate") paginate: Int = 100
    ): Call<MissionResponse>

    @Headers("Content-Type: application/json")
    @GET("mission")
    fun filterMissionsRejected(
        @Query("volunteer") volunteer: String,
        @Query("status") status: String = "rejected",
        @Query("page") page: Int = 1,
        @Query("paginate") paginate: Int = 100
    ): Call<MissionResponse>

    @Headers("Content-Type: application/json")
    @GET("mission")
    fun filterMissionsPending(
        @Query("volunteer") volunteer: String,
        @Query("status") status: String = "pending",
        @Query("page") page: Int = 1,
        @Query("paginate") paginate: Int = 100
    ): Call<MissionResponse>

    @Headers("Content-Type: application/json")
    @GET("mission")
    fun searchMission(
        @Query("title") title: String,
        @Query("page") page: Int = 1,
        @Query("paginate") paginate: Int = 100
    ): Call<MissionResponse>

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

    @Headers("Content-Type: application/json")
    @GET("volunteer/{volunteerId}")
    fun getVolunteer(
        @Path("volunteerId") volunteerId: String
    ): Call<VolunteerDetailData>

    @Headers("Content-Type: application/json")
    @PUT("volunteer")
    fun updateVolunteer(
        @Body volunteer: Volunteer
    ): Call<AddNewVolunteerResponse>

    @Headers("Content-Type: application/json")
    @GET("foundation")
    fun getFoundation(): Call<FoundationResponse>

    @Headers("Content-Type: application/json")
    @PUT("volunteer/{volunteerId}/foundation")
    fun registToFoundation(
        @Path("volunteerId") volunteerId: String,
        @Body foundation: Foundation
    ): Call<GeneralResponse>
}