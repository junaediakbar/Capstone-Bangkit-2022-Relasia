package com.c22ps099.relasiahelpseekerapp.ui.missionDetail

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.ApiConfig
import com.c22ps099.relasiahelpseekerapp.data.api.responses.*
import com.c22ps099.relasiahelpseekerapp.misc.Event
import com.google.gson.Gson
import com.squareup.okhttp.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field

class MissionDetailViewModel(private val token: String): ViewModel(){



    private val _volunteers = MutableLiveData<List<VolunteersItem>>()
    val volunteers: LiveData<List<VolunteersItem>> = _volunteers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    init {
        getVolunteersbyMissions()
    }

    fun deleteMission(){

    }
    fun getVolunteersbyMissions() {
        _isLoading.value = true

        ApiConfig.getApiService().getVoluntersByMission("3850cdb83560ae39c9a0c32bac682c0555ca0771","volunteers")
            .enqueue(object : Callback<VolunteersByMissionResponse> {
                override fun onResponse(
                    call: Call<VolunteersByMissionResponse>,
                    response: Response<VolunteersByMissionResponse>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        _volunteers.value = response.body()?.volunteers as List<VolunteersItem>?
                        Log.v("ini adalah mission:", "${_volunteers.value?.size}")
                    } else {
                        val errorMessage = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            VolunteersByMissionResponse::class.java
                        )
                        _error.value = Event(errorMessage.toString())
                        Log.e("err","${_error.value}")
                    }
                }

                override fun onFailure(call: Call<VolunteersByMissionResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = Event(t.message.toString())
                    Log.e("err","$t")
                }
            })
    }

    fun editStatusVolunteer(id: String,status: String) {
        _isLoading.value = true
        var jsonObject = JSONObject()
        jsonObject.put("id","$id")
        jsonObject.put("status","$status")
        ApiConfig.getApiService().changeVolunteerStatus("3850cdb83560ae39c9a0c32bac682c0555ca0771",jsonObject)
            .enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
//                        _message.value = response.body()?.message
                        Log.v("ini adalah mission:", "${_volunteers.value?.size}")
                    } else {
                        val errorMessage = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            GeneralResponse::class.java
                        )
                        _error.value = Event(errorMessage.toString())
                        Log.e("err","${_error.value}")
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = Event(t.message.toString())
                    Log.e("err","$t")
                }
            })
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val token: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MissionDetailViewModel(token) as T
        }
    }
}