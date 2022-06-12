package com.c22ps099.relasiahelperapp.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelperapp.data.Foundation
import com.c22ps099.relasiahelperapp.network.ApiConfig
import com.c22ps099.relasiahelperapp.network.responses.GeneralResponse
import com.c22ps099.relasiahelperapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoundationDataViewModel : ViewModel() {

    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess: LiveData<Event<Boolean>> = _isSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    fun registFoundation(volunteerId: String, foundation: Foundation) {
        _isLoading.value = true
        ApiConfig.getApiService().registToFoundation(volunteerId, foundation)
            .enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        _isSuccess.value = Event(true)
                    } else {
                        _error.value = Event("Failed: Volunteer Exists")
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
    class Factory :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FoundationDataViewModel() as T
        }
    }
}