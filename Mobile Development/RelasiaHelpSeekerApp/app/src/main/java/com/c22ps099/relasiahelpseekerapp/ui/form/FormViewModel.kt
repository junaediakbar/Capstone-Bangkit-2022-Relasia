package com.c22ps099.relasiahelpseekerapp.ui.form

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelpseekerapp.data.api.ApiConfig
import com.c22ps099.relasiahelpseekerapp.data.api.responses.GeneralResponse
import com.c22ps099.relasiahelpseekerapp.data.api.responses.MissionItem
import com.c22ps099.relasiahelpseekerapp.misc.Event
import com.c22ps099.relasiahelpseekerapp.misc.itemsKab
import com.c22ps099.relasiahelpseekerapp.misc.timeStamp
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormViewModel(private val token: String) : ViewModel() {
    private val _province = MutableLiveData<String>()
    val province: LiveData<String> = _province

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
        _time.value = timeStamp
    }

    fun updateCity(city: String) {
        _city.value = city
    }

    fun updateProvince(prov: String) {
        _province.value = prov
    }

    fun getKabs(prov: String): Array<String> {
        return itemsKab(prov)
    }

    fun postMision(mission: MissionItem) {
        ApiConfig.getApiService().addMission(
            mission.id,
            mission.title,
            mission.address,
            mission.city,
            mission.province,
            mission.numberOfNeeds,
            mission.startDate,
            mission.endDate,
            mission.featuredImage,
            mission.category,
            mission.requirement,
            mission.note,
            mission.volunteers
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
                        Log.e("err", "${_error.value}")
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = Event(t.message.toString())
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