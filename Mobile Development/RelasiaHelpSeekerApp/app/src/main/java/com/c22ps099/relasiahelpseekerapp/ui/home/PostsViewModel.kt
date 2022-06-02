package com.c22ps099.relasiahelpseekerapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelpseekerapp.data.api.ApiConfig
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem
import com.c22ps099.relasiahelpseekerapp.data.api.responses.GeneralResponse
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionsResponse

import com.c22ps099.relasiahelpseekerapp.misc.Event
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostsViewModel(private val token: String): ViewModel(){
    private val _missions = MutableLiveData<List<MissionItem>>()
    val missions: LiveData<List<MissionItem>> = _missions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    init {
        getAllMissions()
    }

    fun getAllMissions() {
        _isLoading.value = true


        ApiConfig.getApiService().getAllMissions()
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

    @Suppress("UNCHECKED_CAST")
    class Factory(private val token: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PostsViewModel(token) as T
        }
    }
}