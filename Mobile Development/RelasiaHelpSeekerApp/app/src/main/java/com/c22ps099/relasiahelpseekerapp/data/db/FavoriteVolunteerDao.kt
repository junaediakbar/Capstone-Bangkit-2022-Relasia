package com.c22ps099.relasiahelpseekerapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersItem


@Dao
interface FavoriteVolunteerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(volunteer: VolunteersItem)

    @Delete
    fun delete(volunteer: VolunteersItem)

    @Query("SELECT * FROM volunteers ORDER BY name DESC")
    fun getAllFavorite(): LiveData<List<VolunteersItem>>

    @Query("SELECT EXISTS(SELECT id FROM volunteers WHERE id = :id)")
    fun isFavorite(id: String): LiveData<Boolean>
}