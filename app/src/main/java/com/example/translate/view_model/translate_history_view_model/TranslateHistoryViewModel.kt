package com.example.translate.view_model.translate_history_view_model

import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.dto.DataModel
import com.example.translate.utils.mapFromRoomTranslateEntityToTranslateEntity
import com.example.translate.view_model.BaseTranslateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TranslateHistoryViewModel(
    private val translateInteractor: ITranslateInteractor<DataModel>,
) : BaseTranslateViewModel(), ITranslateHistoryViewModel {

    override fun onReadListRoomTranslateEntity() {
        onLoadingData()
        viewModelCoroutineScope.launch {
            startLoadingData()
        }
    }

    override fun onInitView() {
        onReadListRoomTranslateEntity()
    }

    private suspend fun startLoadingData() {
        withContext(Dispatchers.IO){
            translateInteractor.readListRoomTranslateEntity().map {
                mapFromRoomTranslateEntityToTranslateEntity(it)
            }.let{
                if (it.isEmpty())
                    onEmptyData(EMPTY_DATA_MODEL)
                else
                    onCorrectData(it)
            }
        }
    }

    companion object {
        private const val EMPTY_DATA_MODEL = "История запросов пуста"
    }

}