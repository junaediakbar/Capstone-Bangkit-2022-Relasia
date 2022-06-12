package com.c22ps099.relasiahelpseekerapp.ui.missionDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelpseekerapp.data.api.ApiConfig
import com.c22ps099.relasiahelpseekerapp.data.api.responses.GeneralResponse
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersByMissionResponse
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersItem
import com.c22ps099.relasiahelpseekerapp.misc.Event
import com.c22ps099.relasiahelpseekerapp.model.UserIdStatus
import com.c22ps099.relasiahelpseekerapp.model.MissionId
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissionDetailViewModel(private val token: String) : ViewModel() {


    private val _volunteers = MutableLiveData<List<VolunteersItem>>()
    val volunteers: LiveData<List<VolunteersItem>> = _volunteers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess: LiveData<Event<Boolean>> = _isSuccess

    private val _isUpdating = MutableLiveData<Boolean>()
    val isUpdating: LiveData<Boolean> = _isUpdating

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    fun deleteMission(missionId: String?) {
        _isLoading.value=true
        val mission = MissionId(missionId)
        ApiConfig.getApiService().deleteMyMission(mission)
            .enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    _isLoading.value = false
                    _isSuccess.value = Event(false)
                    if (response.isSuccessful) {
//                        _message.value = response.body()?.message
                        _isSuccess.value = Event(true)
                        Log.v("ini adalah mission:", "${_volunteers.value?.size}")
                    } else {
                        val errorMessage = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            GeneralResponse::class.java
                        )
                        _error.value = Event(errorMessage.toString())
                        Log.e("err", "${_error.value}")
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = Event(t.message.toString())
                    Log.e("err", "$t")
                }
            })
    }

    fun getVolunteersbyMissions(missionId: String?) {
        _isLoading.value = true

        if (missionId != null) {
            ApiConfig.getApiService().getVoluntersByMission(missionId, "volunteers")
                .enqueue(object : Callback<VolunteersByMissionResponse> {
                    override fun onResponse(
                        call: Call<VolunteersByMissionResponse>,
                        response: Response<VolunteersByMissionResponse>
                    ) {
                        _isLoading.value = false
                        _isUpdating.value= true
                        if (response.isSuccessful) {
                            _volunteers.value = response.body()?.volunteers as List<VolunteersItem>?
                            Log.v("ini adalah mission:", "${_volunteers.value?.size}")
                        } else {
                            Log.e("err", "${_error.value}")
                        }
                    }

                    override fun onFailure(call: Call<VolunteersByMissionResponse>, t: Throwable) {
                        _isLoading.value = false
                        _error.value = Event(t.message.toString())
                        Log.e("err", "$t")
                    }
                }
                )
        }
        _isUpdating.value = false
    }

    fun editStatusVolunteer(missionId: String, id: String, status: String) {
        _isLoading.value = true
        val changeMission = UserIdStatus(
            id, status
        )
        ApiConfig.getApiService().changeVolunteerStatus(missionId, changeMission)
            .enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        getVolunteersbyMissions(missionId)
                        Log.v("ini adalah mission:", "${_volunteers.value?.size}")
                    } else {
                        Log.e("err", "${_error.value}")
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = Event(t.message.toString())
                    Log.e("err", "$t")
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