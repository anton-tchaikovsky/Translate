package com.example.model.data.mapper

import com.example.model.data.TranslateEntity
import com.example.model.data.dto.DataModel
import com.example.model.data.dto.DataModelItem
import com.example.model.data.dto.Translation

object DataMapper {

    fun mapFromDataModeToListTranslateEntity(dataModel: DataModel): List<TranslateEntity> {
        val listTranslateEntity = mutableListOf<TranslateEntity>()
        dataModel.forEach {
            it?.let { dataModelItem ->
                mapFromDataModelItemToTranslateEntity(dataModelItem)?.let { translateEntity ->
                    listTranslateEntity.add(translateEntity)
                }
            }
        }
        return listTranslateEntity.toList()
    }

    internal fun mapFromDataModelItemToTranslateEntity(dataModelItem: DataModelItem) =
        dataModelItem.run {
            if (id == null || text.isNullOrBlank() || meanings.isNullOrEmpty() || meanings[0].translation == null || (meanings[0].translation?.text.isNullOrBlank() && meanings[0].translation?.note.isNullOrBlank()))
                null
            else
                TranslateEntity(
                    id,
                    text,
                    meanings[0].transcription ?: "",
                    mapFromTranslateToTranslateText(meanings[0].translation!!),
                    extractImageUrl(meanings[0].imageUrl)
                )
        }

    internal fun mapFromTranslateToTranslateText(translate: Translation) =
        translate.run {
            if (note.isNullOrBlank())
                if (text.isNullOrBlank())
                    ""
                else
                    text
            else {
                if (text.isNullOrBlank())
                    "$note"
                else
                    "$text ($note)"
            }
        }

    internal fun extractImageUrl(imageUrlDataModelItem: String?) =
        imageUrlDataModelItem.run {
            if (isNullOrBlank())
                ""
            else {
                when (val index = indexOf(HTTPS, 0, true)) {
                    -1 -> ""
                    else -> substring(index).lowercase()
                }
            }
        }

    private const val HTTPS = "https"

}


