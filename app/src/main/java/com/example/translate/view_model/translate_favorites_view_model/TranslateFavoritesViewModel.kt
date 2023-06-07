package com.example.translate.view_model.translate_favorites_view_model

import com.example.model.data.dto.DataModel
import com.example.core.interactor.ITranslateInteractor
import com.example.core.utils.Mapper.mapFromRoomTranslateEntityToTranslateEntity
import com.example.translate.view_model.translate_history_view_model.TranslateHistoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TranslateFavoritesViewModel(override val translateInteractor: ITranslateInteractor<DataModel>) :
    TranslateHistoryViewModel(translateInteractor) {

    override suspend fun startLoadingData() {
        withContext(Dispatchers.IO){
            translateInteractor.readListFavoritesRoomTranslateEntity().map {
                mapFromRoomTranslateEntityToTranslateEntity(it)
            }.let{
                if (it.isEmpty())
                    onEmptyData(EMPTY_DATA)
                else
                    onCorrectData(it)
            }
        }
    }

    companion object {
        private const val EMPTY_DATA = "Список избранных слов пуст"
    }

}