package com.c22ps099.relasiahelpseekerapp.ui.account

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelpseekerapp.data.repository.FavoriteVolunteerRepository


class FavoriteVolunteerViewModel(application: Application) : ViewModel() {

    private val userRepository = FavoriteVolunteerRepository.getInstance(application)

    fun getFavorites() = userRepository.getAllFavoriteUsers()

    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoriteVolunteerViewModel(application) as T
        }
    }

}