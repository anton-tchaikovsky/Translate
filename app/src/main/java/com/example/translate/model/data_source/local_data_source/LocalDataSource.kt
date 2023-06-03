package com.example.translate.model.data_source.local_data_source

import com.example.translate.model.room.RoomTranslateDAO
import com.example.translate.model.room.RoomTranslateEntity

class LocalDataSource (private val roomTranslateDAO: RoomTranslateDAO):ILocalDataSource {

    override fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>) {
        roomTranslateDAO.insertListRoomTranslateEntity(listRoomTranslateEntity)
    }

    override fun readListRoomTranslateEntity(): List<RoomTranslateEntity> =
        roomTranslateDAO.readListRoomTranslateEntity()

    override fun readListRoomTranslateEntity(text: String): List<RoomTranslateEntity> =
        roomTranslateDAO.readListRoomTranslateEntity(text)

    override fun readListFavoritesRoomTranslateEntity(): List<RoomTranslateEntity> =
        roomTranslateDAO.readListFavoritesRoomTranslateEntity()

    override fun updateRoomTranslateEntity(roomTranslateEntity: RoomTranslateEntity) {
        roomTranslateDAO.updateRoomTranslateEntity(roomTranslateEntity)
    }

    override fun readListRoomTranslateEntityById(listId: List<Int>): List<RoomTranslateEntity> =
        roomTranslateDAO.readListRoomTranslateEntityById(listId)

}