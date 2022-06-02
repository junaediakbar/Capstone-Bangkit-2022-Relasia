package com.c22ps099.relasiahelperapp.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem

@Dao
interface MissionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMission(stories: List<MissionDataItem>)

    @Query("SELECT * FROM mission")
    fun getAllMission(): PagingSource<Int, MissionDataItem>

    @Query("DELETE FROM mission")
    suspend fun deleteAllMission()
}