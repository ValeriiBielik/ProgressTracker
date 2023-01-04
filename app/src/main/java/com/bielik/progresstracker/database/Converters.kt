package com.bielik.progresstracker.database

import androidx.room.TypeConverter
import com.bielik.progresstracker.model.common.Day
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun daysListToJson(value: List<Day>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToDaysList(value: String) = Gson().fromJson(value, Array<Day>::class.java).toList()
}