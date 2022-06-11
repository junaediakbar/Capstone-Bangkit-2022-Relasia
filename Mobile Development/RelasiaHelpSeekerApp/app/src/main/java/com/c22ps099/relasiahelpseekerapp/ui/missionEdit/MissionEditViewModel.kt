package com.c22ps099.relasiahelpseekerapp.ui.missionEdit

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelpseekerapp.data.api.ApiConfig
import com.c22ps099.relasiahelpseekerapp.data.api.responses.GeneralResponse
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersItem
import com.c22ps099.relasiahelpseekerapp.misc.Event
import com.c22ps099.relasiahelpseekerapp.model.EditableMission
import com.c22ps099.relasiahelpseekerapp.model.UserIdStatus
import com.c22ps099.relasiahelpseekerapp.ui.missionDetail.MissionDetailViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissionEditViewModel(private val token: String) : ViewModel() {


    private val _volunteers = MutableLiveData<List<VolunteersItem>>()
    val volunteers: LiveData<List<VolunteersItem>> = _volunteers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isUpdating = MutableLiveData<Boolean>()
    val isUpdating: LiveData<Boolean> = _isUpdating

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error


    fun editMisison(editableMission: EditableMission) {
        _isLoading.value = true
        ApiConfig.getApiService().editMission(editableMission)
            .enqueue(object : Callback<GeneralResponse> {
                override fun onResponse(
                    call: Call<GeneralResponse>,
                    response: Response<GeneralResponse>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        Log.v("ini adalah mission:", "${_volunteers.value?.size}")
                    } else {
                        Log.e("err", "${_error.value}")
                    }
                }

                override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = Event(t.message.toString())
                    Log.e("err", "$t")
                }
            })
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val token: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MissionEditViewModel(token) as T
        }
    }

}
