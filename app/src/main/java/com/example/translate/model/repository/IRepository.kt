package com.example.translate.model.repository

import com.example.translate.model.room.RoomTranslateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IRepository <T: Any> {

    suspend fun getDataModel(text: String): Flow<T>

    fun registerNetworkCallback(): StateFlow<Boolean>

    fun unregisterNetworkCallback()

    fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>)

    fun readListRoomTranslateEntity(): List<RoomTranslateEntity>

    fun readListRoomTranslateEntity(text: String): List<RoomTranslateEntity>


}