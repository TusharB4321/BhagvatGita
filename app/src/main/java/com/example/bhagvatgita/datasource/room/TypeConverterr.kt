package com.example.bhagvatgita.datasource.room

import androidx.room.TypeConverter
import com.example.bhagvatgita.datasource.model.Commentary
import com.example.bhagvatgita.datasource.model.Translation
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

    @TypeConverter
    fun fromTransToString(list:List<Translation>):String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromStringToTrans(string: String):List<Translation>{
        return Gson().fromJson(string,object :TypeToken<List<Translation>>(){}.type)
    }

    @TypeConverter
    fun fromCommToString(list:List<Commentary>):String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromStringToComm(string: String):List<Commentary>{
        return Gson().fromJson(string,object :TypeToken<List<Commentary>>(){}.type)
    }
}