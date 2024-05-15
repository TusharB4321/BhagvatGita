package com.example.bhagvatgita.datasource.apis

import com.example.bhagvatgita.datasource.model.ChaptersModelItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiInterface {

    @Headers(
        "Accept: application/json",
        "X-RapidAPI-Key: e4680b8618mshbf2a89d86e1a6a4p1ab615jsne53368195451",
        "X-RapidAPI-Host: bhagavad-gita3.p.rapidapi.com"
    )
    @GET("/v2/chapters/")
    fun getAllChapters(): Call<List<ChaptersModelItem>>
}