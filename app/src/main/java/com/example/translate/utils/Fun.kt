package com.example.translate.utils

import com.example.translate.model.data.TranslateEntity
import com.example.translate.model.data.dto.DataModelItem
import com.example.translate.model.data.dto.Translation
import com.example.translate.model.room.RoomTranslateEntity

fun mapFromDataModelItemToTranslateEntity(dataModelItem: DataModelItem) =
    dataModelItem.run {
        TranslateEntity(
            id,
            text,
            meanings[0].transcription,
            mapFromTranslateToTranslateText(meanings[0].translation),
            extractImageUrl(meanings[0].imageUrl)
        )
    }

fun mapFromTranslateToTranslateText(translate: Translation) =
    translate.run {
        if (note.isNullOrBlank())
            text
        else
            "$text ($note)"
    }

fun extractImageUrl (imageUrlDataModelItem:String) =
    imageUrlDataModelItem.run{
        substring(indexOf(HTTPS))
    }

fun mapFromTranslateEntityToRoomTranslateEntity(translateEntity: TranslateEntity) =
    translateEntity.run {
        RoomTranslateEntity(
            id,
            text,
            transcription,
            textTranslation,
            imageUrl,
            isFavorites.toString()
        )
    }

fun mapFromRoomTranslateEntityToTranslateEntity(roomTranslateEntity: RoomTranslateEntity) =
    roomTranslateEntity.run {
        var isFavorites = false
        if (this.isFavorites == "true")
            isFavorites = true
        TranslateEntity(
            id,
            text,
            transcription,
            textTranslation,
            imageUrl,
            isFavorites
        )
    }

const val HTTPS = "https"
