package com.example.bhagvatgita.datasource.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverterr {

    @TypeConverter
    fun fromListToString(list:List<String>):String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(string: String):List<String>{
        return Gson().fromJson(string,object :TypeToken<List<String>>(){}.type)
    }
}