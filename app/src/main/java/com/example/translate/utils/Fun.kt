package com.example.translate.utils

import com.example.translate.model.data.TranslateEntity
import com.example.translate.model.data.dto.DataModelItem
import com.example.translate.model.data.dto.Translation

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

const val HTTPS = "https"
