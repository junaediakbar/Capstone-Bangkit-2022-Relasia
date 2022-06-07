package com.c22ps099.relasiahelperapp.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.c22ps099.relasiahelperapp.data.MissionRepository
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem

class HomeViewModel(pref: MissionRepository) : ViewModel() {

    private val _title = MutableLiveData<String>()
    var title: LiveData<String> = _title

    fun updateTitle(title: String) {
        _title.value = title
    }

    val missions: LiveData<PagingData<MissionDataItem>> =
        pref.getMissionsPages().cachedIn(viewModelScope)
}