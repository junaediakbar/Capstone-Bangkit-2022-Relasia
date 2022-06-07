package com.c22ps099.relasiahelperapp.ui.home

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.c22ps099.relasiahelperapp.data.MissionRepository
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.network.responses.MissionDetailResponse
import com.c22ps099.relasiahelperapp.network.responses.VolunteerDetailData
import com.c22ps099.relasiahelperapp.ui.missionDetail.MissionDetailViewModel
import com.c22ps099.relasiahelperapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(pref: MissionRepository) : ViewModel() {

//    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    val missions: LiveData<PagingData<MissionDataItem>> =
        pref.getMissionsPages().cachedIn(viewModelScope)

//    val results = currentQuery.switchMap { queryString ->
//        pref.getSearchResults(queryString).cachedIn(viewModelScope)
//    }

//    fun searchMission(query: String) {
//        currentQuery.value = query
//    }

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

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "mission"
        private const val TAG = "HomeViewModel"
    }
}