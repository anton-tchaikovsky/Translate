package com.example.translate.interactor.translate_interactor

import com.example.repository.room.RoomTranslateEntity
import kotlinx.coroutines.flow.Flow

interface ITranslateInteractor<T> {

    suspend fun getDataModel(text: String): Flow<T>

    fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>)

    fun readListRoomTranslateEntity(text: String): List<RoomTranslateEntity>

    fun readListRoomTranslateEntityById(listId: List<Int>): List<RoomTranslateEntity>


}