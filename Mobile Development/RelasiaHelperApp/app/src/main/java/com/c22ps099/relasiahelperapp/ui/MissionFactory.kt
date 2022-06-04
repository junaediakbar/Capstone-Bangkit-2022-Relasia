package com.c22ps099.relasiahelperapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelperapp.data.MissionRepository
import com.c22ps099.relasiahelperapp.ui.bookmarks.BookmarksViewModel
import com.c22ps099.relasiahelperapp.ui.home.HomeViewModel
import com.c22ps099.relasiahelperapp.ui.missions.MissionsViewModel

class MissionFactory(private val pref: MissionRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MissionsViewModel::class.java) -> {
                MissionsViewModel(pref) as T
            }
            modelClass.isAssignableFrom(BookmarksViewModel::class.java) -> {
                BookmarksViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}