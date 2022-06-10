package com.example.sudoku.computation

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object Converters {
    @TypeConverter
    fun fromString(value: String?): Array<IntArray> {
        val listType: Type = object : TypeToken<Array<IntArray?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArray(list: Array<IntArray?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}