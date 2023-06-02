package com.example.translate.model.data_source.local_data_source

import com.example.translate.model.room.RoomTranslateEntity

interface ILocalDataSource {

    fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>)

    fun readListRoomTranslateEntity(): List<RoomTranslateEntity>

}