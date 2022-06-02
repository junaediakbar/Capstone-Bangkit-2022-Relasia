package com.c22ps099.relasiahelperapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.c22ps099.relasiahelperapp.network.responses.MissionDataItem

@Database(
    entities = [MissionDataItem::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class MissionDatabase : RoomDatabase() {
    abstract fun missionDao(): MissionDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: MissionDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MissionDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MissionDatabase::class.java, "mission_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}