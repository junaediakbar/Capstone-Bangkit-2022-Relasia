package com.c22ps099.relasiahelperapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "bookmarked_mission")
@Parcelize
data class MissionMark(

    @ColumnInfo(name = "end_date")
    val endDate: String,

    @ColumnInfo("note")
    val note: String,

    @ColumnInfo("address")
    val address: String,

    @ColumnInfo("city")
    val city: String,

    @ColumnInfo("requirement")
    val requirement: String,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("featured_image")
    val featuredImage: List<String>,

    @ColumnInfo("number_of_needs")
    val numberOfNeeds: String,

    @ColumnInfo("province")
    val province: String,

    @PrimaryKey
    @ColumnInfo("id")
    val id: String,

    @ColumnInfo("category")
    val category: String,

    @ColumnInfo("start_date")
    val startDate: String,

    @ColumnInfo("timestamp")
    val timestamp: String
) : Parcelable