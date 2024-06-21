package com.example.bhagvatgita.datasource.sharedPref

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(val context: Context) {

    val sharedPreferences:SharedPreferences by lazy {
        context.getSharedPreferences("saved_chapters",Context.MODE_PRIVATE)
    }

    fun getAllSaveChapterKeySp():Set<String>{
        return sharedPreferences.all.keys
    }

    fun putSavedChapterSp(key:String,value:Int){
        sharedPreferences.edit().putInt(key,value).apply()
    }

    fun deleteSavedChapterFromSp(key:String){
        sharedPreferences.edit().remove(key).apply()
    }
}