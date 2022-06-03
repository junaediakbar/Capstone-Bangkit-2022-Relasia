package com.c22ps099.relasiahelpseekerapp.data.api

import com.c22ps099.relasiahelpseekerapp.data.api.responses.GeneralResponse
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionsResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("mission")
    fun getAllMissions(
    ): Call<MissionsResponse>

    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("mission")
    fun addMission(
        @Field("id") id: String?,
        @Field("title") title: String?,
        @Field("address") address: String?,
        @Field("city") city: String?,
        @Field("province") province: String?,
        @Field("number_of_needs") number_of_needs: String?,
        @Field("start_date") start_date: String?,
        @Field("end_date") end_date: String?,
        @Field("featured_image") featured_image: List<String?>?,
        @Field("category") category: String?,
        @Field("requirement") requirement: String?,
        @Field("note") note: String?,
        @Field("volunteers") volunteers: HashMap<String, String>?
    ): Call<GeneralResponse>
}