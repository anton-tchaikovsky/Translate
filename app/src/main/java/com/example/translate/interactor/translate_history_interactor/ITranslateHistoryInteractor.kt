package com.example.translate.interactor.translate_history_interactor

import com.example.repository.room.RoomTranslateEntity

interface ITranslateHistoryInteractor {

    fun readListRoomTranslateEntity(): List<RoomTranslateEntity>

}