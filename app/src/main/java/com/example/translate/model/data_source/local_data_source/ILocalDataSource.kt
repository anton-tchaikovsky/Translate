package com.example.translate.model.data_source.local_data_source

import com.example.translate.model.room.RoomTranslateEntity

interface ILocalDataSource {

    fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>)

    fun readListRoomTranslateEntity(): List<RoomTranslateEntity>

    fun readListFavoritesRoomTranslateEntity(): List<RoomTranslateEntity>

    fun readListRoomTranslateEntity(text: String): List<RoomTranslateEntity>

    fun updateRoomTranslateEntity(roomTranslateEntity: RoomTranslateEntity)

    fun readListRoomTranslateEntityById(listId: List<Int>): List<RoomTranslateEntity>

}