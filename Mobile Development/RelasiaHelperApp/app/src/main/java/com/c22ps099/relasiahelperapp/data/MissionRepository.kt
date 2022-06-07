package com.c22ps099.relasiahelperapp.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.c22ps099.relasiahelperapp.network.ApiService
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem

class MissionRepository(
    private val apiService: ApiService
) {
    fun getMissionsPages(): LiveData<PagingData<MissionDataItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
                maxSize = 20
            ),
            pagingSourceFactory = {
                MissionPagingSource(apiService)
            }
        ).liveData
    }
}