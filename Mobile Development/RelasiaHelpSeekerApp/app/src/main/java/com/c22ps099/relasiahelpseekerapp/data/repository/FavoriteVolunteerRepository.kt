package com.c22ps099.relasiahelpseekerapp.data.repository

import android.app.Application
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersItem
import com.c22ps099.relasiahelpseekerapp.data.db.FavoriteVolunteerDao
import com.c22ps099.relasiahelpseekerapp.data.db.FavoriteVolunteerRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteVolunteerRepository private constructor(application: Application) {

    private val mFavoriteUserDao: FavoriteVolunteerDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteVolunteerRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers() = mFavoriteUserDao.getAllFavorite()

    fun insertFavorite(user: VolunteersItem) {
        executorService.execute { mFavoriteUserDao.insert(user) }
    }

    fun deleteFavorite(user: VolunteersItem) {
        executorService.execute { mFavoriteUserDao.delete(user) }
    }

    fun isFavorite(id: String) = mFavoriteUserDao.isFavorite(id)

    companion object {
        @Volatile
        private var INSTANCE: FavoriteVolunteerRepository? = null

        @JvmStatic
        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(FavoriteVolunteerRepository::class.java) {
                FavoriteVolunteerRepository(application)
            }.also { INSTANCE = it }
    }
}