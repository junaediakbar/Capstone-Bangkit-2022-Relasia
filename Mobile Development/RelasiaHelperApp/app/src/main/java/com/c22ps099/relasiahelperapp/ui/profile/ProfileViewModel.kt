package com.c22ps099.relasiahelperapp.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelperapp.data.Volunteer
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.network.responses.AddNewVolunteerResponse
import com.c22ps099.relasiahelperapp.utils.Event
import com.c22ps099.relasiahelperapp.utils.itemsKab
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val token: String) : ViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess: LiveData<Event<Boolean>> = _isSuccess

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _province = MutableLiveData<String>()
    val province: LiveData<String> = _province

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> = _city

    fun updateCity(city: String) {
        _city.value = city
    }

    fun updateProvince(prov: String) {
        _province.value = prov
    }

    fun addNewVolunteer(volunteer: Volunteer) {
        _isLoading.value = true

        ApiConfig.getApiService().addVolunteer(volunteer)
            .enqueue(object : Callback<AddNewVolunteerResponse> {
                override fun onResponse(
                    call: Call<AddNewVolunteerResponse>,
                    response: Response<AddNewVolunteerResponse>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        Log.v(TAG, "Successs")
                    } else {
                        val errorMessage = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            AddNewVolunteerResponse::class.java
                        )
                        _error.value = Event(errorMessage.message!!)
                        Log.e("err", "${_error.value}")
                    }
                }

                override fun onFailure(call: Call<AddNewVolunteerResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = Event(t.message.toString())
                    Log.e("err", "$t")
                }
            })
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val token: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(token) as T
        }
    }
}