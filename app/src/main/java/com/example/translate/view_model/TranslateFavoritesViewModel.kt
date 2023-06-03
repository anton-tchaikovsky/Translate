package com.example.translate.view_model

import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.dto.DataModel
import com.example.translate.utils.mapFromRoomTranslateEntityToTranslateEntity
import com.example.translate.view_model.translate_history_view_model.TranslateHistoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TranslateFavoritesViewModel(private val translateInteractor: ITranslateInteractor<DataModel>) :
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