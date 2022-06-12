package com.c22ps099.relasiahelperapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem

@Dao
interface MissionMarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMissionMark(mission: MissionDataItem)

    @Delete
    fun deleteMissionMark(mission: MissionDataItem)

    @Query("SELECT * FROM mission")
    fun getAllBookmarkedMissions(): LiveData<List<MissionDataItem>>

    @Query("SELECT EXISTS(SELECT id FROM mission WHERE id = :id)")
    fun isBookmarkedMission(id: String): LiveData<Boolean>
}