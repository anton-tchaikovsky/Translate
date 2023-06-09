package com.example.core.interactor

import com.example.repository.room.RoomTranslateEntity

interface IChangingFavoritesStateInteractor {

    fun updateRoomTranslateEntity(roomTranslateEntity: RoomTranslateEntity)

}