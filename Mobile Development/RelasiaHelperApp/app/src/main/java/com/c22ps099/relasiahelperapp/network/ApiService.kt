package com.c22ps099.relasiahelperapp.network

import com.c22ps099.relasiahelperapp.network.responses.MissionResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("mission")
    suspend fun getAllMissions(
        @Query("length") size: Int = 5
    ): MissionResponse
}