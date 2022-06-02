package com.c22ps099.relasiahelperapp.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.c22ps099.relasiahelperapp.network.ApiService
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MissionRepository(
    private val missionDatabase: MissionDatabase,
    private val apiService: ApiService
) {
    fun getMissionsPages(): LiveData<PagingData<MissionDataItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = MissionRemoteMediator(missionDatabase, apiService),
            pagingSourceFactory = {
                missionDatabase.missionDao().getAllMission()
            }
        ).liveData
    }

//    suspend fun addNewStory(
//        multipart: MultipartBody.Part,
//        params: HashMap<String, RequestBody>,
//    ) = !apiService.addStory(multipart, params, auth).error
}