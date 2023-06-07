package com.example.repository.repository

import com.example.model.data.dto.DataModel
import com.example.repository.data_source.local_data_source.ILocalDataSource
import com.example.repository.data_source.remote_data_source.IRemoteDataSource
import com.example.repository.room.RoomTranslateEntity

class Repository(
    private val remoteDataSource: IRemoteDataSource<DataModel>,
    private val localDataSource: ILocalDataSource
) : IRepository<DataModel> {

    override suspend fun getDataModel(text: String) =
        remoteDataSource.getDataModel(text)

    override fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>) {
        localDataSource.insertListRoomTranslateEntity(listRoomTranslateEntity)
    }

    override fun readListRoomTranslateEntity(): List<RoomTranslateEntity> =
       localDataSource.readListRoomTranslateEntity()

    override fun readListRoomTranslateEntity(text: String): List<RoomTranslateEntity> =
        localDataSource.readListRoomTranslateEntity(text)

    override fun readListFavoritesRoomTranslateEntity(): List<RoomTranslateEntity> =
        localDataSource.readListFavoritesRoomTranslateEntity()

    override fun updateRoomTranslateEntity(roomTranslateEntity: RoomTranslateEntity) {
        localDataSource.updateRoomTranslateEntity(roomTranslateEntity)
    }

    override fun readListRoomTranslateEntityById(listId: List<Int>): List<RoomTranslateEntity> =
        localDataSource.readListRoomTranslateEntityById(listId)

}