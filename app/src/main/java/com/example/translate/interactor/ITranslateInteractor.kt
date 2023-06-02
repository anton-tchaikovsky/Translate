package com.example.translate.interactor

import com.example.translate.model.room.RoomTranslateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ITranslateInteractor<T> {

    suspend fun getDataModel(text: String): Flow<T>

    fun registerNetworkCallback(): StateFlow<Boolean>

    fun unregisterNetworkCallback()

    fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>)

    fun readListRoomTranslateEntity(): List<RoomTranslateEntity>

}