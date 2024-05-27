package com.example.bhagvatgita.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bhagvatgita.datasource.model.ChaptersModelItem
import com.example.bhagvatgita.datasource.model.VersesItem
import com.example.bhagvatgita.datasource.room.AppDatabase
import com.example.bhagvatgita.datasource.room.SaveChapters
import com.example.bhagvatgita.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class MainViewmodel(application: Application):AndroidViewModel(application) {

    val chapterDao=AppDatabase.getDatabaseInstance(application)?.saveChapterDao()
    val repository=MainRepository(chapterDao!!)

    fun getAllChapters(): Flow<List<ChaptersModelItem>> = repository.getAllChapters()

    fun getAllVerses(chapterNumber:Int):Flow<List<VersesItem>> =repository.getAllVerses(chapterNumber)
    fun getPerticularVerse(chapterNumber:Int,verseNumber:Int):Flow<VersesItem> =repository.getPerticularVerse(chapterNumber,verseNumber)

    //for offline data storage
    suspend fun insertChapters(saveChapter: SaveChapters)=repository.insertChapters(saveChapter)

    fun getSavedChapter(): LiveData<List<SaveChapters>> =repository.getSavedChapter()



}