package com.example.translate.view_model.translate_history_view_model

import com.example.model.data.dto.DataModel
import com.example.core.interactor.ITranslateInteractor
import com.example.core.utils.Mapper.mapFromRoomTranslateEntityToTranslateEntity
import com.example.core.view_model.BaseTranslateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class TranslateHistoryViewModel(
    override val translateInteractor: ITranslateInteractor<DataModel>
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

    protected open suspend fun startLoadingData() {
        withContext(Dispatchers.IO) {
            translateInteractor.readListRoomTranslateEntity().map {
                mapFromRoomTranslateEntityToTranslateEntity(it)
            }.let {
                if (it.isEmpty())
                    onEmptyData(EMPTY_DATA)
                else
                    onCorrectData(it)
            }
        }
    }

    companion object {
        private const val EMPTY_DATA = "История запросов пуста"
    }

}