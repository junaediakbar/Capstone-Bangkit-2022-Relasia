package com.c22ps099.relasiahelperapp.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.c22ps099.relasiahelperapp.network.ApiService
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem

class MissionRepository(
    private val apiService: ApiService
) {
    fun getMissionsPages(volunteerId: String): LiveData<PagingData<MissionDataItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
                initialLoadSize = 5
            ),
            pagingSourceFactory = {
                MissionPagingSource(apiService, volunteerId)
            }
        ).liveData
    }
}