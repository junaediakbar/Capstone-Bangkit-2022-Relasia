package com.c22ps099.relasiahelpseekerapp.ui.account

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersItem
import com.c22ps099.relasiahelpseekerapp.data.repository.FavoriteVolunteerRepository

class VolunteerAccountViewModel(id: String, application: Application) : ViewModel() {
    private val volunteerFavoriteRepository = FavoriteVolunteerRepository.getInstance(application)

    val isFavorite = volunteerFavoriteRepository.isFavorite(id)

    fun setFavorite(volunteer: VolunteersItem, isFavorite: Boolean) {
        if (isFavorite) {
            volunteerFavoriteRepository.insertFavorite(volunteer)
        } else {
            volunteerFavoriteRepository.deleteFavorite(volunteer)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val id: String, private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VolunteerAccountViewModel(id, application) as T
        }
    }
}