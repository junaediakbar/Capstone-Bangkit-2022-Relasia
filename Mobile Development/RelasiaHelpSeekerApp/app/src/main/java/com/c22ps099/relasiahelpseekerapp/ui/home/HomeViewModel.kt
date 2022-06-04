package com.c22ps099.relasiahelpseekerapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelpseekerapp.data.api.ApiConfig
import com.c22ps099.relasiahelpseekerapp.data.api.responses.*

import com.c22ps099.relasiahelpseekerapp.misc.Event
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(private val token: String): ViewModel(){
    private val _missions = MutableLiveData<List<MissionItem>>()
    val missions: LiveData<List<MissionItem>> = _missions

    private val _foundations = MutableLiveData<List<Foundation>>()
    val foundations: LiveData<List<Foundation>> = _foundations

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    init {
        getAllMissions()
        getAllFoundations()
    }

    fun getAllMissions() {
        _isLoading.value = true

        ApiConfig.getApiService().getAllMissions("helpseeker.id2")
            .enqueue(object : Callback<MissionsResponse> {
                override fun onResponse(
                    call: Call<MissionsResponse>,
                    response: Response<MissionsResponse>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        _missions.value = response.body()?.data as List<MissionItem>?
                        Log.v("ini adalah mission:", "${_missions.value?.size}")
                    } else {
                        val errorMessage = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            GeneralResponse::class.java
                        )
                        _error.value = Event(errorMessage.message)
                        Log.e("err","${_error.value}")
                    }
                }

                override fun onFailure(call: Call<MissionsResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = Event(t.message.toString())
                }
            })
    }

    fun getAllFoundations() {
        _isLoading.value = true

        ApiConfig.getApiService().getFoundation()
            .enqueue(object : Callback<FoundationsResponse> {
                override fun onResponse(
                    call: Call<FoundationsResponse>,
                    response: Response<FoundationsResponse>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        _foundations.value = response.body()?.data as List<Foundation>?
                        Log.v("ini adalah mission:", "${_foundations.value?.size}")
                    } else {
                        val errorMessage = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            GeneralResponse::class.java
                        )
                        _error.value = Event(errorMessage.message)
                        Log.e("err","${_error.value}")
                    }
                }

                override fun onFailure(call: Call<FoundationsResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = Event(t.message.toString())
                }
            })
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val token: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(token) as T
        }
    }
}