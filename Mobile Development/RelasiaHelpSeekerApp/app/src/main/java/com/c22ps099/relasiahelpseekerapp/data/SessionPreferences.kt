package com.c22ps099.relasiahelpseekerapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map


class SessionPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val tokenKey = stringPreferencesKey("token")

    fun getSavedToken() = dataStore.data.map {
        it[tokenKey] ?: ""
    }

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[tokenKey] = token
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SessionPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>) = INSTANCE ?: synchronized(this) {
            SessionPreferences(dataStore)
        }.also { INSTANCE = it }
    }
}