package com.example.bhagvatgita.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bhagvatgita.datasource.model.ChaptersModelItem
import com.example.bhagvatgita.datasource.model.VersesItem
import com.example.bhagvatgita.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class MainViewmodel:ViewModel() {

    val repository=MainRepository()

    fun getAllChapters(): Flow<List<ChaptersModelItem>> = repository.getAllChapters()

    fun getAllVerses(chapterNumber:Int):Flow<List<VersesItem>> =repository.getAllVerses(chapterNumber)
    fun getPerticularVerse(chapterNumber:Int,verseNumber:Int):Flow<VersesItem> =repository.getPerticularVerse(chapterNumber,verseNumber)
}