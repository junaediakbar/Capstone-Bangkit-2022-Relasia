package com.c22ps099.relasiahelperapp.network

import com.c22ps099.relasiahelperapp.data.Mission
import com.c22ps099.relasiahelperapp.network.responses.GeneralResponse
import com.c22ps099.relasiahelperapp.network.responses.MissionResponse
import com.c22ps099.relasiahelperapp.network.responses.VolunteerResponse
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
    @PUT("volunteer/{volunteerId}/mission")
    fun applyToMission(
        @Path("volunteerId") volunteerId: String,
        @Body mission: Mission
    ): Call<GeneralResponse>
}