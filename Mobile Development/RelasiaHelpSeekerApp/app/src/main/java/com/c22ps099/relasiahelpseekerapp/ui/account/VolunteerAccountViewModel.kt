package com.c22ps099.relasiahelpseekerapp.ui.account

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelpseekerapp.data.api.ApiConfig
import com.c22ps099.relasiahelpseekerapp.data.api.responses.AddNewHelpSeekerResponse
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersItem
import com.c22ps099.relasiahelpseekerapp.data.repository.FavoriteVolunteerRepository
import com.c22ps099.relasiahelpseekerapp.misc.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
//    TODO: Implement After Make API to Get Foundation by Volunteers Id
//    fun getVolunteerFoundations(id : String){
//        ApiConfig.getApiService().getFoundationByVolunteerId(id)
//            .enqueue(object : Callback<AddNewHelpSeekerResponse> {
//                override fun onResponse(
//                    call: Call<AddNewHelpSeekerResponse>,
//                    response: Response<AddNewHelpSeekerResponse>
//                ) {
//                    _isSuccess.value = Event(false)
//                    _isLoading.value = false
//
//                    if (response.isSuccessful) {
//                        _isSuccess.value = Event(true)
//                        Log.v("ini adalah mission:", "Successs")
//                    } else {
//                        Log.e("err", "${_error.value}")
//                    }
//                }
//
//                override fun onFailure(call: Call<AddNewHelpSeekerResponse>, t: Throwable) {
//                    _error.value = Event(t.message.toString())
//                    Log.e("err", "$t")
//                }
//            })
//    }


    @Suppress("UNCHECKED_CAST")
    class Factory(private val id: String, private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VolunteerAccountViewModel(id, application) as T
        }
    }
}