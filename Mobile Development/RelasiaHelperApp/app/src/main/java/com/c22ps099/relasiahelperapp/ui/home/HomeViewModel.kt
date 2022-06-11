package com.c22ps099.relasiahelperapp.ui.home

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.c22ps099.relasiahelperapp.data.MissionRepository
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.network.responses.MissionResponse
import com.c22ps099.relasiahelperapp.network.responses.VolunteerDetailData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(pref: MissionRepository, volunteerId: String) : ViewModel() {

    private val _listMission = MutableLiveData<List<MissionDataItem?>?>()
    val listMission: LiveData<List<MissionDataItem?>?> = _listMission

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _volunteerName = MutableLiveData<String?>()
    val volunteerName: LiveData<String?> = _volunteerName

    val missions: LiveData<PagingData<MissionDataItem>> =
        pref.getMissionsPages(volunteerId).cachedIn(viewModelScope)

    fun searchMission(query: String) {
        _isLoading.value = true
        ApiConfig.getApiService().searchMission(query)
            .enqueue(object : Callback<MissionResponse> {
                override fun onResponse(
                    call: Call<MissionResponse>,
                    response: Response<MissionResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _isSuccess.value = true
                        _listMission.value = response.body()?.data
                    } else {
                        _isSuccess.value = false
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<MissionResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isSuccess.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun checkVolunteer(volunteerId: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getVolunteer(volunteerId)
            .enqueue(object : Callback<VolunteerDetailData> {
                override fun onResponse(
                    call: Call<VolunteerDetailData>,
                    response: Response<VolunteerDetailData>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _isSuccess.value = true
                        _volunteerName.value = response.body()?.name
                    } else {
                        _isSuccess.value = false
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<VolunteerDetailData>, t: Throwable) {
                    _isLoading.value = false
                    _isSuccess.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val pref: MissionRepository,
        private val volunteerId: String
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(pref, volunteerId) as T
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}