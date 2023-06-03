package com.example.translate.model.data

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class TranslateEntity(
    val id: Int,
    val text: String,
    val transcription: String,
    val textTranslation: String,
    val imageUrl: String,
    var isFavorites: Boolean = false
): Parcelable
