package com.c22ps099.relasiahelperapp.ui.bookmarks

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c22ps099.relasiahelperapp.data.MissionRepository
import kotlinx.coroutines.launch

class BookmarksViewModel(private val pref: MissionRepository) : ViewModel() {

//    private val userRepository = UserRepository.getInstance(application)
//
//    fun getFavorites() = userRepository.getAllFavUsers()
//
//    fun getThemeSettings(): LiveData<Boolean> {
//        return pref.getThemeSetting().asLiveData()
//    }
//
//    fun saveThemeSetting(isDarkModeActive: Boolean) {
//        viewModelScope.launch {
//            pref.saveThemeSetting(isDarkModeActive)
//        }
//    }

}