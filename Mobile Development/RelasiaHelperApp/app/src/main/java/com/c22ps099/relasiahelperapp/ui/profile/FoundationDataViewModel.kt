package com.c22ps099.relasiahelperapp.ui.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps099.relasiahelperapp.network.responses.FoundationDataItem
import com.c22ps099.relasiahelperapp.ui.missionDetail.MissionDetailViewModel

class FoundationDataViewModel(
    foundationId: String,
    application: Application
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val foundation: FoundationDataItem,
        private val application: Application
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MissionDetailViewModel(foundation.id, application) as T
        }
    }
}