package com.example.bhagvatgita.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bhagvatgita.datasource.model.ChaptersModelItem
import com.example.bhagvatgita.datasource.model.VersesItem
import com.example.bhagvatgita.datasource.room.AppDatabase
import com.example.bhagvatgita.datasource.room.SaveChapters
import com.example.bhagvatgita.datasource.room.SavedVerses
import com.example.bhagvatgita.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class MainViewmodel(application: Application):AndroidViewModel(application) {

    val chapterDao=AppDatabase.getDatabaseInstance(application)?.saveChapterDao()
    val verseDao=AppDatabase.getDatabaseInstance(application)?.saveVersesDao()
    val repository=MainRepository(chapterDao!!,verseDao!!)

    fun getAllChapters(): Flow<List<ChaptersModelItem>> = repository.getAllChapters()

    fun getAllVerses(chapterNumber:Int):Flow<List<VersesItem>> =repository.getAllVerses(chapterNumber)
    fun getPerticularVerse(chapterNumber:Int,verseNumber:Int):Flow<VersesItem> =repository.getPerticularVerse(chapterNumber,verseNumber)

    //for offline data storage
    suspend fun insertChapters(saveChapter: SaveChapters)=repository.insertChapters(saveChapter)

    fun getSavedChapter(): LiveData<List<SaveChapters>> =repository.getSavedChapter()

    fun getPerticularChapter(chapter_number:Int):LiveData<SaveChapters> =repository.getPerticularChapter(chapter_number)


    //offline Verses Data Storage

    suspend fun insertVerses(versesItem: SavedVerses) =repository.insertVerses(versesItem)
    fun getAllVerse(): LiveData<List<SavedVerses>> =repository.getAllVerse()
    fun getPerticularVerses(chapter_number:Int,verse_number:Int): LiveData<SavedVerses> =repository.getPerticularVerses(chapter_number,verse_number)
    fun deletePerticularVerses(chapter_number:Int,verse_number:Int)=repository.deletePerticularVerses(chapter_number,verse_number)

}