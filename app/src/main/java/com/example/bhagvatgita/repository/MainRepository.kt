package com.example.bhagvatgita.repository

import com.example.bhagvatgita.datasource.apis.ApiUtilities
import com.example.bhagvatgita.datasource.model.ChaptersModelItem
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
}