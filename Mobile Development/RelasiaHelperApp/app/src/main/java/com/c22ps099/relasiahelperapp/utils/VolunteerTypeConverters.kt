package com.c22ps099.relasiahelperapp.utils

import androidx.room.TypeConverter
import com.c22ps099.relasiahelperapp.network.responses.VolunteersItemStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class VolunteerTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<VolunteersItemStatus?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<VolunteersItemStatus?>?>() {}.type
        return gson.fromJson<List<VolunteersItemStatus?>>(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<VolunteersItemStatus?>?): String? {
        return gson.toJson(someObjects)
    }
}