package com.c22ps099.relasiahelperapp.ui.missionDetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelperapp.data.Mission
import com.c22ps099.relasiahelperapp.data.MissionMarkRepository
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.network.responses.GeneralResponse
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.network.responses.MissionDetailResponse
import com.c22ps099.relasiahelperapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissionDetailViewModel(
    missionId: String,
    application: Application
) : ViewModel() {

    companion object {
        private const val TAG = "MissionDetailViewModel"
    }

    private val _detailMission = MutableLiveData<MissionDetailResponse>()
    val detailMission: LiveData<MissionDetailResponse> = _detailMission

    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess: LiveData<Event<Boolean>> = _isSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val missionMarkRepository = MissionMarkRepository.getInstance(application)

    val isBookMission = missionMarkRepository.isBookmarkedMission(missionId)

    fun setBookMission(mission: MissionDataItem, isBookMission: Boolean) {
        if (isBookMission) {
            missionMarkRepository.insertMissionMark(mission)
        } else {
            missionMarkRepository.deleteMissionMark(mission)
        }
    }

    fun showDetailMission(missionId: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getMissionDetail(missionId)
            .enqueue(object : Callback<MissionDetailResponse> {
                override fun onResponse(
                    call: Call<MissionDetailResponse>,
                    response: Response<MissionDetailResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _detailMission.value = response.body()
                    } else {
                        _error.value = Event("ERROR: DETAIL MISSION")
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<MissionDetailResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = Event("ERROR: DETAIL MISSION")
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun applyMission(volunteerId: String, mission: Mission) {
        _isLoading.value = true
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
                    _error.value = Event("Failed: Volunteer Exists")
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
        private val mission: MissionDataItem,
        private val application: Application
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MissionDetailViewModel(mission.id, application) as T
        }
    }
}