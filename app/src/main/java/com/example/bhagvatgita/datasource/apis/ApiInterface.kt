package com.example.bhagvatgita.datasource.apis

import com.example.bhagvatgita.datasource.model.ChaptersModelItem
import com.example.bhagvatgita.datasource.model.VersesItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {
    @GET("/v2/chapters/")
    fun getAllChapters(): Call<List<ChaptersModelItem>>

    @GET("v2/chapters/{chapterNumber}/verses/")
    fun getAllVerses(@Path("chapterNumber")chapterNumber:Int):Call<List<VersesItem>>

    @GET("v2/chapters/{chapterNumber}/verses/{verseNumber}/")
    fun  getPerticularVerse(@Path("chapterNumber")chapterNumber: Int,
                             @Path("verseNumber")verseNumber:Int):Call<VersesItem>
}