package com.example.translate.interactor.translate_favorites_interactor

import com.example.model.data.dto.DataModel
import com.example.repository.repository.IRepository
import com.example.repository.room.RoomTranslateEntity
import com.example.translate.interactor.translate_history_interactor.ITranslateHistoryInteractor

class TranslateFavoritesInteractor(private val repository: IRepository<DataModel>):
    ITranslateHistoryInteractor {

    override fun readListRoomTranslateEntity(): List<RoomTranslateEntity> =
        repository.readListFavoritesRoomTranslateEntity()

}