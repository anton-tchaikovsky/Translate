package com.example.translate.unit

import com.example.translate.model.data.TranslateEntity
import com.example.translate.model.data.dto.DataModelItem
import com.example.translate.model.data.dto.Translation

fun mapFromDataModelItemToTranslateEntity(dataModelItem: DataModelItem) =
    dataModelItem.run {
        TranslateEntity(
            id,
            text,
            meanings[0].transcription,
            mapFromTranslateToTranslateText(meanings[0].translation)
        )
    }

fun mapFromTranslateToTranslateText(translate: Translation) =
    translate.run {
        if (note.isNullOrBlank())
            text
        else
            "$text ($note)"
    }
