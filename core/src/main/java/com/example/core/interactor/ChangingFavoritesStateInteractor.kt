package com.example.core.interactor

import com.example.model.data.dto.DataModel
import com.example.repository.repository.IRepository
import com.example.repository.room.RoomTranslateEntity

class ChangingFavoritesStateInteractor(private val repository: IRepository<DataModel>) :
    IChangingFavoritesStateInteractor {

    override fun updateRoomTranslateEntity(roomTranslateEntity: RoomTranslateEntity) {
        repository.updateRoomTranslateEntity(roomTranslateEntity)
    }

}