package com.example.core.utils

import com.example.model.data.TranslateEntity
import com.example.repository.room.RoomTranslateEntity

object Mapper {

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
}

