package com.c22ps099.relasiahelpseekerapp.ui.bookmarks

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelpseekerapp.data.repository.FavoriteVolunteerRepository

class BookmarksViewModel(application: Application) : ViewModel() {

    private val missionMarkRepository = FavoriteVolunteerRepository.getInstance(application)

    fun getBookmarkedVolunteers() = missionMarkRepository.getAllFavoriteUsers()

    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookmarksViewModel(application) as T
        }
    }

}