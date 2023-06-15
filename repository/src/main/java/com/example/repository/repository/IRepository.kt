package com.example.repository.repository

import com.example.repository.room.RoomTranslateEntity
import kotlinx.coroutines.flow.Flow

interface IRepository <T: Any> {

    suspend fun getDataModel(text: String): Flow<T>

    fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>)

    fun readListRoomTranslateEntity(): List<RoomTranslateEntity>

    fun readListRoomTranslateEntity(text: String): List<RoomTranslateEntity>

    fun readListFavoritesRoomTranslateEntity(): List<RoomTranslateEntity>

    fun updateRoomTranslateEntity(roomTranslateEntity: RoomTranslateEntity)

    fun readListRoomTranslateEntityById(listId: List<Int>): List<RoomTranslateEntity>

}