package com.c22ps099.relasiahelperapp.ui.missions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.network.responses.MissionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissionStatusViewModel : ViewModel() {

    private val _listMission = MutableLiveData<List<MissionDataItem?>>()
    val listMission: LiveData<List<MissionDataItem?>> = _listMission

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun callBack() = object : Callback<MissionResponse> {
        override fun onResponse(
            call: Call<MissionResponse>,
            response: Response<MissionResponse>
        ) {
            _isLoading.value = false
            if (response.isSuccessful) {
                _listMission.value = response.body()?.data
            } else {
                Log.e(TAG, "onFailure: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<MissionResponse>, t: Throwable) {
            _isLoading.value = false
            Log.e(TAG, "onFailure: ${t.message.toString()}")
        }
    }

    fun filterMissionAccepted(volunteerId: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().filterMissionsAccepted(volunteerId)
        client.enqueue(callBack())
    }

    fun filterMissionRejected(volunteerId: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().filterMissionsRejected(volunteerId)
        client.enqueue(callBack())
    }

    fun filterMissionPending(volunteerId: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().filterMissionsPending(volunteerId)
        client.enqueue(callBack())
    }

    companion object {
        private const val TAG = "FollowViewModel"
    }
}