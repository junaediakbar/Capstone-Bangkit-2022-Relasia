package com.c22ps099.relasiahelperapp.ui.bookmarks

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelperapp.data.MissionMarkRepository

class BookmarksViewModel(application: Application) : ViewModel() {

    private val missionMarkRepository = MissionMarkRepository.getInstance(application)

    fun getBookmarkedMissions() = missionMarkRepository.getAllBookmarkedMissions()

    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookmarksViewModel(application) as T
        }
    }

}