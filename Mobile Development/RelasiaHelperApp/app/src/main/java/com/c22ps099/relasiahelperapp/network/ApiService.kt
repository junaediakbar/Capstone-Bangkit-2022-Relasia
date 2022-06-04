package com.c22ps099.relasiahelperapp.network

import com.c22ps099.relasiahelperapp.network.responses.MissionResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("mission")
    suspend fun getAllMissions(
        @Query("page") page: Int = 1,
        @Query("paginate") paginate: Int = 5
    ): MissionResponse
}