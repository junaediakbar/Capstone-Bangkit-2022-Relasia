package com.c22ps099.relasiahelperapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.c22ps099.relasiahelperapp.network.ApiService
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem

class MissionStatusPagingSource(private val apiService: ApiService, private val volunteerId: String) : PagingSource<Int, MissionDataItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MissionDataItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getMissionsStatus(volunteerId, page, params.loadSize)

            LoadResult.Page(
                data = responseData.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.data.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MissionDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}