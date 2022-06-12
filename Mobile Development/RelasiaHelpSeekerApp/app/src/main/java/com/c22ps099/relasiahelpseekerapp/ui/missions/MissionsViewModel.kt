package com.c22ps099.relasiahelpseekerapp.ui.missions

import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissionsViewModel(private val volunteerId: String) :
    ViewModel() {

    private val _listMission = MutableLiveData<List<MissionItem?>?>()
    val listMission: LiveData<List<MissionItem?>?> = _listMission

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess


    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val volunteerId: String
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MissionsViewModel(volunteerId) as T
        }
    }

    companion object {
        private const val TAG = "MissionsViewModel"
    }
}