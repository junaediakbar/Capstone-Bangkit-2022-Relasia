//package com.c22ps099.relasiahelperapp.data
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room.Companion.databaseBuilder
//import androidx.room.RoomDatabase
//
//@Database(entities = [MissionMark::class], version = 1)
//abstract class MissionMarkDatabase : RoomDatabase() {
//
//    abstract fun missionMarkDao(): MissionMarkDao
//
//    companion object {
//
//        @Volatile
//        private var INSTANCE: MissionMarkDatabase? = null
//
//        @JvmStatic
//        fun getDatabase(context: Context) =
//            INSTANCE ?: synchronized(MissionMarkDatabase::class.java) {
//                databaseBuilder(
//                    context.applicationContext,
//                    MissionMarkDatabase::class.java, "bookmarked_mission_database"
//                )
//                    .build()
//            }.also { INSTANCE = it }
//    }
//}