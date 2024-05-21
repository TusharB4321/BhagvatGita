package com.example.bhagvatgita.repository

import com.example.bhagvatgita.datasource.apis.ApiUtilities
import com.example.bhagvatgita.datasource.model.ChaptersModelItem
import com.example.bhagvatgita.datasource.model.VersesItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {


    fun getAllChapters(): Flow<List<ChaptersModelItem>> = callbackFlow {
        val callback=object :Callback<List<ChaptersModelItem>>{
            override fun onResponse(
                call: Call<List<ChaptersModelItem>>,
                response: Response<List<ChaptersModelItem>>
            ) {
                if (response.isSuccessful&&response.body()!=null){
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<List<ChaptersModelItem>>, t: Throwable) {
                close(t)
            }

        }

        ApiUtilities.api.getAllChapters().enqueue(callback)
        awaitClose{}
    }

    fun getAllVerses(chapterNumber:Int):Flow<List<VersesItem>> = callbackFlow {

        val callback=object :Callback<List<VersesItem>>{
            override fun onResponse(
                call: Call<List<VersesItem>>,
                response: Response<List<VersesItem>>
            ) {
                if (response.isSuccessful&&response.body()!=null){
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<List<VersesItem>>, t: Throwable) {
                close(t)
            }

        }

        ApiUtilities.api.getAllVerses(chapterNumber).enqueue(callback)
        awaitClose {}
    }

    fun getPerticularVerse(chapterNumber: Int,verseNumber:Int):Flow<VersesItem> = callbackFlow{
        val callback= object :Callback<VersesItem>{
            override fun onResponse(call: Call<VersesItem>, response: Response<VersesItem>) {

                if (response.isSuccessful &&response.body()!=null){
                    trySend(response.body()!!)
                    close()
                }
            }
            override fun onFailure(call: Call<VersesItem>, t: Throwable) {
                close(t)
            }
        }

        ApiUtilities.api.getPerticularVerse(chapterNumber,verseNumber).enqueue(callback)
        awaitClose {}
    }
}