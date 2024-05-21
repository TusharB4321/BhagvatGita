package com.example.bhagvatgita.datasource.apis

import android.content.Context
import android.view.WindowManager
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.bhagvatgita.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiUtilities {

    private const val BASE_URL="https://bhagavad-gita3.p.rapidapi.com"


    val headers= mapOf<String,String>(
        "Accept" to "application/json",
        "X-RapidAPI-Key" to "e4680b8618mshbf2a89d86e1a6a4p1ab615jsne53368195451",
        "X-RapidAPI-Host" to "bhagavad-gita3.p.rapidapi.com")



    val client=OkHttpClient.Builder().apply {
        addInterceptor {chain->
        val newRequest=chain.request().newBuilder().apply {
            headers.forEach{ (key, value) ->addHeader(key,value)}
        }.build()
            chain.proceed(newRequest)
        }
    }.build()

    val api:ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

}

