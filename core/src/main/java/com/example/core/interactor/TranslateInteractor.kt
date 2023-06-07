package com.example.core.interactor

import com.example.model.data.dto.DataModel
import com.example.repository.repository.IRepository
import com.example.repository.room.RoomTranslateEntity

class TranslateInteractor(private val repository: IRepository<DataModel>) :
    ITranslateInteractor<DataModel> {

    override suspend fun getDataModel(text: String) = repository.getDataModel(text)

    override fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>) {
        repository.insertListRoomTranslateEntity(listRoomTranslateEntity)
    }

    override fun readListRoomTranslateEntity(): List<RoomTranslateEntity> =
        repository.readListRoomTranslateEntity()

    override fun readListRoomTranslateEntity(text: String): List<RoomTranslateEntity> =
        repository.readListRoomTranslateEntity(text)

    override fun readListFavoritesRoomTranslateEntity(): List<RoomTranslateEntity> =
        repository.readListFavoritesRoomTranslateEntity()

    override fun updateRoomTranslateEntity(roomTranslateEntity: RoomTranslateEntity) {
        repository.updateRoomTranslateEntity(roomTranslateEntity)
    }

    override fun readListRoomTranslateEntityById(listId: List<Int>): List<RoomTranslateEntity> =
        repository.readListRoomTranslateEntityById(listId)


}