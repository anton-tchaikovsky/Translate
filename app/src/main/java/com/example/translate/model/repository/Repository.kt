package com.example.translate.model.repository

import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_source.local_data_source.ILocalDataSource
import com.example.translate.model.data_source.remote_data_source.IRemoteDataSource
import com.example.translate.model.network.INetworkStatus
import com.example.translate.model.room.RoomTranslateEntity
import kotlinx.coroutines.flow.StateFlow

class Repository(
    private val remoteDataSource: IRemoteDataSource<DataModel>,
    private val localDataSource: ILocalDataSource,
    private val netWorkStatus: INetworkStatus
) : IRepository<DataModel> {

    override suspend fun getDataModel(text: String) =
        remoteDataSource.getDataModel(text)

    override fun registerNetworkCallback(): StateFlow<Boolean> =
        netWorkStatus.registerNetworkCallback()

    override fun unregisterNetworkCallback() {
        netWorkStatus.unregisterNetworkCallback()
    }

    override fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>) {
        localDataSource.insertListRoomTranslateEntity(listRoomTranslateEntity)
    }

    override fun readListRoomTranslateEntity(): List<RoomTranslateEntity> =
       localDataSource.readListRoomTranslateEntity()

    override fun readListRoomTranslateEntity(text: String): List<RoomTranslateEntity> =
        localDataSource.readListRoomTranslateEntity(text)

}