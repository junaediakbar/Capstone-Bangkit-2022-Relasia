package com.c22ps099.relasiahelpseekerapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.c22ps099.relasiahelpseekerapp.data.api.responses.VolunteersItem
import com.c22ps099.relasiahelpseekerapp.misc.FoundationTypeConverter

@Database(entities = [VolunteersItem::class], version = 1)
@TypeConverters(FoundationTypeConverter::class)
abstract class FavoriteVolunteerRoomDatabase : RoomDatabase() {

    abstract fun favoriteUserDao(): FavoriteVolunteerDao

    companion object {

        @Volatile
        private var INSTANCE: FavoriteVolunteerRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context) = INSTANCE ?: synchronized(FavoriteVolunteerRoomDatabase::class.java) {
            databaseBuilder(context.applicationContext,
                FavoriteVolunteerRoomDatabase::class.java, "favorite_volunteer_database")
                .build()
        }.also { INSTANCE = it }
    }
}