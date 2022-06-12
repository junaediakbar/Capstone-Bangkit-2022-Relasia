package com.c22ps099.relasiahelpseekerapp.ui.missions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c22ps099.relasiahelpseekerapp.data.api.ApiConfig
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionsResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissionStatusViewModel : ViewModel() {

    private val _listMission = MutableLiveData<List<MissionItem?>>()
    val listMission: LiveData<List<MissionItem?>> = _listMission

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun callBack(option: String) = object : Callback<MissionsResponse> {
        override fun onResponse(
            call: Call<MissionsResponse>,
            response: Response<MissionsResponse>
        ) {
            _isLoading.value = false
            if (response.isSuccessful) {
                _status.value=option
                _listMission.value = response.body()?.data as List<MissionItem?>
            } else {
                Log.e(TAG, "onFailure: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<MissionsResponse>, t: Throwable) {
            _isLoading.value = false
            Log.e(TAG, "onFailure: ${t.message.toString()}")
        }
    }

    fun filterMission(volunteerId: String, active: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFilteredMissions(volunteerId,active)
        client.enqueue(callBack(active))
    }

    companion object {
        private const val TAG = "FollowViewModel"
    }
}