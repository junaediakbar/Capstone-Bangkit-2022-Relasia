package com.c22ps099.relasiahelperapp.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.c22ps099.relasiahelperapp.network.ApiService
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem

class MissionRepository(
    private val missionDatabase: MissionDatabase,
    private val apiService: ApiService
) {
    fun getMissionsPages(title: String?): LiveData<PagingData<MissionDataItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
                maxSize = 20
            ),
            remoteMediator = MissionRemoteMediator(missionDatabase, apiService, title.toString()),
            pagingSourceFactory = {
                missionDatabase.missionDao().getAllMission(title.toString())
            }
        ).liveData
    }
}