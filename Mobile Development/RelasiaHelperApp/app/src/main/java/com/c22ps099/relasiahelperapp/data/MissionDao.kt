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
    suspend fun insertMission(missions: List<MissionDataItem>)

    @Query("SELECT * FROM mission WHERE title = :title")
    fun getAllMission(title: String): PagingSource<Int, MissionDataItem>

    @Query("DELETE FROM mission")
    suspend fun deleteAllMission()
}