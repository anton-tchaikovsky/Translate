package com.example.translate.view_model.translate_history_view_model

import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.TranslateEntity
import com.example.translate.model.data.app_state.AppState
import com.example.translate.model.data.dto.DataModel
import com.example.translate.utils.mapFromRoomTranslateEntityToTranslateEntity
import com.example.translate.utils.mapFromTranslateEntityToRoomTranslateEntity
import com.example.translate.view_model.BaseTranslateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class TranslateHistoryViewModel(
    private val translateInteractor: ITranslateInteractor<DataModel>
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

    override fun onChangingFavoritesState(translateEntity: TranslateEntity) {
        val updatePosition = listTranslateEntity.indexOf(translateEntity)
        val updateTranslateEntity = translateEntity.apply {
            isFavorites = !isFavorites
        }
        viewModelCoroutineScope.launch {
            withContext(Dispatchers.IO){
                listTranslateEntity[updatePosition] = updateTranslateEntity
                translateInteractor.updateRoomTranslateEntity(
                    mapFromTranslateEntityToRoomTranslateEntity(listTranslateEntity[updatePosition])
                )
            }
            translateLiveData.postValue(AppState.SuccessChangeFavorites(updatePosition, listTranslateEntity))
        }
    }

    protected open suspend fun startLoadingData() {
        withContext(Dispatchers.IO){
            translateInteractor.readListRoomTranslateEntity().map {
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
        private const val EMPTY_DATA = "История запросов пуста"
    }

}