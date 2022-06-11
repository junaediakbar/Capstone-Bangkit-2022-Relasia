package com.c22ps099.relasiahelpseekerapp.ui.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelpseekerapp.data.api.ApiConfig
import com.c22ps099.relasiahelpseekerapp.data.api.responses.AddNewHelpSeekerResponse
import com.c22ps099.relasiahelpseekerapp.data.api.responses.GeneralResponse
import com.c22ps099.relasiahelpseekerapp.data.api.responses.HelpseekerDataResponse

import com.c22ps099.relasiahelpseekerapp.misc.Event
import com.c22ps099.relasiahelpseekerapp.model.Helpseeker
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AccountViewModel(private val token: String,private val id: String) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess: LiveData<Event<Boolean>> = _isSuccess

    private val _helpseeker = MutableLiveData<HelpseekerDataResponse?>()
    val helpseeker: LiveData<HelpseekerDataResponse?> = _helpseeker

    private val _province = MutableLiveData<String>()
    val province: LiveData<String> = _province

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> = _city

    init {
        getHelpseeker(id)
    }

    fun updateCity(city: String) {
        _city.value = city
    }

    fun updateProvince(prov: String) {
        _province.value = prov
    }


    fun addNewHelpSeeker(helpSeeker: Helpseeker) {
        _isLoading.value = true

        ApiConfig.getApiService().addHelpseeker(helpSeeker)
            .enqueue(object : Callback<AddNewHelpSeekerResponse> {
                override fun onResponse(
                    call: Call<AddNewHelpSeekerResponse>,
                    response: Response<AddNewHelpSeekerResponse>
                ) {
                    _isSuccess.value = Event(false)
                    _isLoading.value = false

                   if (response.isSuccessful) {
                       _isSuccess.value = Event(true)
                        Log.v("ini adalah mission:", "Successs")
                    } else {
                        Log.e("err", "${_error.value}")
                    }
                }

                override fun onFailure(call: Call<AddNewHelpSeekerResponse>, t: Throwable) {
                    _error.value = Event(t.message.toString())
                    Log.e("err", "$t")
                }
            })
    }

    fun updateHelpseeker(helpSeeker: Helpseeker) {
        _isLoading.value = true

        ApiConfig.getApiService().updateHelpseeker(helpSeeker)
            .enqueue(object : Callback<AddNewHelpSeekerResponse> {
                override fun onResponse(
                    call: Call<AddNewHelpSeekerResponse>,
                    response: Response<AddNewHelpSeekerResponse>
                ) {
                    _isSuccess.value = Event(false)
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        _isSuccess.value = Event(true)
                        Log.v("ini adalah mission:", "Successs")
                    } else {
                        Log.e("err", "${_error.value}")
                    }
                }

                override fun onFailure(call: Call<AddNewHelpSeekerResponse>, t: Throwable) {
                    _error.value = Event(t.message.toString())
                    _isLoading.value = false
                    Log.e("err", "$t")
                }
            })
    }

    fun getHelpseeker(id: String){
        _isLoading.value = true
        ApiConfig.getApiService().getSpecificHelpseeker(id)
            .enqueue(object : Callback<HelpseekerDataResponse> {
                override fun onResponse(
                    call: Call<HelpseekerDataResponse>,
                    response: Response<HelpseekerDataResponse>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        _helpseeker.value= response.body() as HelpseekerDataResponse
                        Log.v("ini adalah mission:", "Successs")
                    } else {
                        Log.v("there is error","failed")
                    }
                }

                override fun onFailure(call: Call<HelpseekerDataResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = Event(t.message.toString())
                    Log.e("err", "$t")
                }
            })
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val token: String,private val uid: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AccountViewModel(token,uid) as T
        }
    }
}