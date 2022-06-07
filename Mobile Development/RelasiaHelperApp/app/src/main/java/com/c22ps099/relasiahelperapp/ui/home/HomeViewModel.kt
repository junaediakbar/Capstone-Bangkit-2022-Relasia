package com.c22ps099.relasiahelperapp.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.c22ps099.relasiahelperapp.data.MissionRepository
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem

class HomeViewModel(pref: MissionRepository, state: SavedStateHandle) : ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val missions: LiveData<PagingData<MissionDataItem>> =
        pref.getMissionsPages().cachedIn(viewModelScope)

    val results = currentQuery.switchMap { queryString ->
        pref.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchMission(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "mission"
    }
}