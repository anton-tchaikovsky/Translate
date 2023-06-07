package com.example.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "translate_table")
data class RoomTranslateEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "eng_text")
    val text: String,
    @ColumnInfo(name = "transcription")
    val transcription: String,
    @ColumnInfo(name = "rus_text")
    val textTranslation: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo (name = "is_favorites")
    var isFavorites: String = "false"
)
