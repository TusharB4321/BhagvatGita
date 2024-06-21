package com.example.bhagvatgita.datasource.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedChapterDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertChapters(saveChapter:SaveChapters)

    @Query("SELECT * FROM SavedChapter")
    fun getSavedChapter():LiveData<List<SaveChapters>>

    @Query("DELETE FROM SavedChapter WHERE id= :id")
    suspend fun deleteChapter(id:Int)

    @Query("SELECT * FROM SavedChapter WHERE chapter_number= :chapter_number")
    fun getPerticularChapter(chapter_number:Int):LiveData<SaveChapters>

}