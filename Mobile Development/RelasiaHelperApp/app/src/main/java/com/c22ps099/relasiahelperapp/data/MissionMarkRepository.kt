//package com.c22ps099.relasiahelperapp.data
//
//import android.app.Application
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//
//class MissionMarkRepository private constructor(application: Application) {
//    private val mMissionMarkDao: MissionMarkDao
//    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
//
//    init {
//        val db = MissionMarkDatabase.getDatabase(application)
//        mMissionMarkDao = db.missionMarkDao()
//    }
//
//    fun getAllBookmarkedMissions() = mMissionMarkDao.getAllBookmarkedMissions()
//
//    fun insertMissionMark(missionMark: MissionMark) {
//        executorService.execute { mMissionMarkDao.insertMissionMark(missionMark) }
//    }
//
//    fun deleteMissionMark(missionMark: MissionMark) {
//        executorService.execute { mMissionMarkDao.deleteMissionMark(missionMark) }
//    }
//
//    fun isBookmarkedMission(id: String) = mMissionMarkDao.isBookmarkedMission(id)
//
//    companion object {
//        @Volatile
//        private var INSTANCE: MissionMarkRepository? = null
//
//        @JvmStatic
//        fun getInstance(application: Application) =
//            INSTANCE ?: synchronized(MissionMarkRepository::class.java) {
//                MissionMarkRepository(application)
//            }.also { INSTANCE = it }
//    }
//}