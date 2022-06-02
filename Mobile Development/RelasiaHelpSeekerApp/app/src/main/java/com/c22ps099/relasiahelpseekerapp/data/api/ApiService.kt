package com.c22ps099.relasiahelpseekerapp.data.api

import com.c22ps099.relasiahelpseekerapp.data.api.responses.GeneralResponse
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionsResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
//    @FormUrlEncoded
//    @POST("register")
//    fun register(
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Call<MessageResponse>
    @Multipart
    @POST("mission")
    fun addMission(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<GeneralResponse>


    @GET("mission")
    fun getAllMissions(
    ): Call<MissionsResponse>
}