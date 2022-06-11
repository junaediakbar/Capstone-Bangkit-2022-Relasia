package com.c22ps099.relasiahelperapp.ui.missions

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.c22ps099.relasiahelperapp.data.MissionRepository
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.network.responses.MissionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissionsViewModel(pref: MissionRepository, private val volunteerId: String) :
    ViewModel() {

    private val _listMission = MutableLiveData<List<MissionDataItem?>?>()
    val listMission: LiveData<List<MissionDataItem?>?> = _listMission

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    val missionsStatus: LiveData<PagingData<MissionDataItem>> =
        pref.getMissionsStatusPages(volunteerId).cachedIn(viewModelScope)

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val pref: MissionRepository,
        private val volunteerId: String
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MissionsViewModel(pref, volunteerId) as T
        }
    }

    companion object {
        private const val TAG = "MissionsViewModel"
    }
}