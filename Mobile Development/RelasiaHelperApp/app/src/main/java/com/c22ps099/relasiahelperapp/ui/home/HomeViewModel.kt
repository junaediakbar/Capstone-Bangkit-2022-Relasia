package com.c22ps099.relasiahelperapp.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.c22ps099.relasiahelperapp.data.MissionRepository

class HomeViewModel(pref: MissionRepository) : ViewModel() {

    val missions = pref.getMissionsPages().cachedIn(viewModelScope)
}