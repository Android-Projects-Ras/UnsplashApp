package com.example.myapp.data.cache

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun listToJson(list: List<Any>): String =
        gson.toJson(list)

    @TypeConverter
    fun jsonToList(value: String): List<Any> {
        if (value == null)
            return Collections.emptyList()

        val listType = object: TypeToken<List<Any>>(){}.type
        return gson.fromJson(value, listType)
    }
}