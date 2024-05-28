package com.example.bhagvatgita.repository

import androidx.lifecycle.LiveData
import com.example.bhagvatgita.datasource.apis.ApiUtilities
import com.example.bhagvatgita.datasource.model.ChaptersModelItem
import com.example.bhagvatgita.datasource.model.VersesItem
import com.example.bhagvatgita.datasource.room.SaveChapters
import com.example.bhagvatgita.datasource.room.SavedChapterDao
import com.example.bhagvatgita.datasource.room.SavedVerses
import com.example.bhagvatgita.datasource.room.SavedVersesDao
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(val savedChapterDao: SavedChapterDao,val savedVersesDao: SavedVersesDao) {
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

    // Offline chapter Data storage
    suspend fun insertChapters(saveChapter: SaveChapters)=savedChapterDao.insertChapters(saveChapter)

    fun getSavedChapter(): LiveData<List<SaveChapters>> =savedChapterDao.getSavedChapter()

    fun getPerticularChapter(chapter_number:Int):LiveData<SaveChapters> =savedChapterDao.getPerticularChapter(chapter_number)

    //offline Verses Data Storage

    suspend fun insertVerses(versesItem: SavedVerses) =savedVersesDao.insertVerses(versesItem)
    fun getAllVerse(): LiveData<List<SavedVerses>> =savedVersesDao.getAllVerse()
    fun getPerticularVerses(chapter_number:Int,verse_number:Int): LiveData<SavedVerses> =savedVersesDao.getPerticularVerses(chapter_number,verse_number)
    fun deletePerticularVerses(chapter_number:Int,verse_number:Int)=savedVersesDao.deletePerticularVerses(chapter_number,verse_number)
}