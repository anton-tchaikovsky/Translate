package com.example.translate.interactor

import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.repository.IRepository
import com.example.translate.model.room.RoomTranslateEntity
import kotlinx.coroutines.flow.StateFlow

class TranslateInteractor(private val repository: IRepository<DataModel>) :
    ITranslateInteractor<DataModel> {

    override suspend fun getDataModel(text: String) = repository.getDataModel(text)

    override fun registerNetworkCallback(): StateFlow<Boolean> =
        repository.registerNetworkCallback()

    override fun unregisterNetworkCallback() {
        repository.unregisterNetworkCallback()
    }

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