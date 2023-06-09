package com.example.model.data.mapper

import com.example.model.data.dto.DataModelItem
import com.example.model.data.dto.Translation

object DataMapper {

    fun mapFromDataModelItemToTranslateEntity(dataModelItem: DataModelItem) =
        dataModelItem.run {
            com.example.model.data.TranslateEntity(
                id,
                text,
                meanings[0].transcription,
                mapFromTranslateToTranslateText(meanings[0].translation),
                extractImageUrl(meanings[0].imageUrl)
            )
        }

    private fun mapFromTranslateToTranslateText(translate: Translation) =
        translate.run {
            if (note.isNullOrBlank())
                text
            else
                "$text ($note)"
        }

    private fun extractImageUrl (imageUrlDataModelItem:String) =
        imageUrlDataModelItem.run{
            substring(indexOf(HTTPS))
        }

    private const val HTTPS = "https"

}


