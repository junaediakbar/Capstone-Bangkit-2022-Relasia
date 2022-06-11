package com.c22ps099.relasiahelpseekerapp.ui.form

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelpseekerapp.data.api.ApiConfig
import com.c22ps099.relasiahelpseekerapp.data.api.responses.GeneralResponse
import com.c22ps099.relasiahelpseekerapp.misc.Event
import com.c22ps099.relasiahelpseekerapp.misc.itemsKab

import com.c22ps099.relasiahelpseekerapp.model.Mission
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormViewModel(private val token: String) : ViewModel() {
    private val _province = MutableLiveData<String>()
    val province: LiveData<String> = _province

    private val _requirements = MutableLiveData<String>()
    val requirements: LiveData<String> = _requirements

    private val _notes= MutableLiveData<String>()
    val notes: LiveData<String> = _notes

    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess: LiveData<Event<Boolean>> = _isSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    private val _cities = MutableLiveData<Array<String>>()
    val cities: LiveData<Array<String>> = _cities

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> = _city

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    init {
    }

    fun updateCity(city: String) {
        _city.value = city
    }

    fun updateProvince(prov: String) {
        _province.value = prov
    }

    fun updateRequirements(req: String) {
        _requirements.value = req
    }

    fun updateNotes(req: String) {
        _notes.value = req
    }

    fun getKabs(prov: String): Array<String> {
        return itemsKab(prov)
    }

    fun postMision(mission: Mission) {
        ApiConfig.getApiService().addMission(
            mission
        )
            .enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        _isSuccess.value = Event(true)
                    } else {
                        val errorMessage = Gson().fromJson(
                            response.errorBody()?.charStream(),
                            GeneralResponse::class.java
                        )
                        _error.value = Event(errorMessage.message)
                        Log.e("response err", "${_error.value}")
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = Event(t.message.toString())
                    Log.e("request err", "$t")
                }
            })
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val token: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FormViewModel(token) as T
        }
    }
}