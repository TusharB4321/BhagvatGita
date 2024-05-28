package com.example.bhagvatgita.datasource.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bhagvatgita.datasource.model.VersesItem

@Dao
interface SavedVersesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerses(versesItem: SavedVerses)

    @Query("SELECT * FROM SavedVerses")
    fun getAllVerse(): LiveData<List<SavedVerses>>

    @Query("SELECT * FROM SavedVerses WHERE chapter_number= :chapter_number AND verse_number= :verse_number")
    fun getPerticularVerses(chapter_number:Int,verse_number:Int): LiveData<SavedVerses>

    @Query("DELETE FROM SavedVerses WHERE chapter_number= :chapter_number AND verse_number= :verse_number")
    fun deletePerticularVerses(chapter_number:Int,verse_number:Int)
}