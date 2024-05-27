package com.example.bhagvatgita.datasource.room

import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName ="SavedChapter")
    data class SaveChapters(
        val chapter_number: Int,
        val chapter_summary: String,
        val chapter_summary_hindi: String,
        @PrimaryKey
        val id: Int,
        val name: String,
        val name_meaning: String,
        val name_translated: String,
        val name_transliterated: String,
        val slug: String,
        val verses_count: Int,
        val verses:List<String>
    )

