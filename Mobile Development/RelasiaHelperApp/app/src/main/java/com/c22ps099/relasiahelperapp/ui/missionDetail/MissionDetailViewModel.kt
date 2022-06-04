package com.c22ps099.relasiahelperapp.ui.missionDetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelperapp.data.Mission
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.network.responses.GeneralResponse
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.network.responses.VolunteerResponse
import com.c22ps099.relasiahelperapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissionDetailViewModel(
    private val volunteerId: String,
    private val missionId: String
) : ViewModel() {

    companion object {
        private const val TAG = "MissionDetailViewModel"
    }

    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess: LiveData<Event<Boolean>> = _isSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    fun applyMission(volunteerId: String, mission: Mission) {
        ApiConfig.getApiService().applyToMission(volunteerId, mission).enqueue(object :
            Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _isSuccess.value = Event(true)
                } else {
                    Log.e("response err", "${_error.value}")
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = Event(t.message.toString())
                Log.e("request err", "$t")
            }
        })
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val volunteerId: String,
        private val mission: MissionDataItem
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MissionDetailViewModel(volunteerId, mission.id) as T
        }
    }
}