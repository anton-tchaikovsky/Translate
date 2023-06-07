package com.example.core.interactor

import com.example.repository.room.RoomTranslateEntity
import kotlinx.coroutines.flow.Flow

interface ITranslateInteractor<T> {

    suspend fun getDataModel(text: String): Flow<T>

    fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>)

    fun readListRoomTranslateEntity(): List<RoomTranslateEntity>

    fun readListRoomTranslateEntity(text: String): List<RoomTranslateEntity>

    fun readListFavoritesRoomTranslateEntity(): List<RoomTranslateEntity>

    fun updateRoomTranslateEntity(roomTranslateEntity: RoomTranslateEntity)

    fun readListRoomTranslateEntityById(listId: List<Int>): List<RoomTranslateEntity>

}