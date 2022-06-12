package com.c22ps099.relasiahelperapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.Companion.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem
import com.c22ps099.relasiahelperapp.utils.ImageTypeConverters

@Database(entities = [MissionDataItem::class], version = 1, exportSchema = false)
@TypeConverters(ImageTypeConverters::class)
abstract class MissionMarkDatabase : RoomDatabase() {

    abstract fun missionMarkDao(): MissionMarkDao

    companion object {

        @Volatile
        private var INSTANCE: MissionMarkDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context) =
            INSTANCE ?: synchronized(MissionMarkDatabase::class.java) {
                databaseBuilder(
                    context.applicationContext,
                    MissionMarkDatabase::class.java, "bookmarked_mission_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }.also { INSTANCE = it }
    }
}