package com.c22ps099.relasiahelperapp.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.c22ps099.relasiahelperapp.data.MissionRepository

class HomeViewModel(pref: MissionRepository) : ViewModel() {

    private val _title = MutableLiveData<String>()
    var title: LiveData<String> = _title

    fun updateTitle(title: String) {
        _title.value = title
    }

    val missions = pref.getMissionsPages(title.toString()).cachedIn(viewModelScope)
}