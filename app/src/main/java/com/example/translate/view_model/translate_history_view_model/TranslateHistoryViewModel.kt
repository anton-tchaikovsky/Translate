package com.example.translate.view_model.translate_history_view_model

import com.example.core.interactor.IChangingFavoritesStateInteractor
import com.example.core.utils.Mapper.mapFromRoomTranslateEntityToTranslateEntity
import com.example.core.view_model.BaseTranslateViewModel
import com.example.model.data.app_state.AppState
import com.example.translate.interactor.translate_history_interactor.ITranslateHistoryInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class TranslateHistoryViewModel(
    changingFavoritesStateInteractor: IChangingFavoritesStateInteractor,
    private val translateHistoryInteractor: ITranslateHistoryInteractor
) : BaseTranslateViewModel(changingFavoritesStateInteractor), ITranslateHistoryViewModel {

    open val emptiData = "История запросов пуста"

    override fun onReadListRoomTranslateEntity() {
        onLoadingData()
        viewModelCoroutineScope.launch {
            startLoadingData()
        }
    }

    override fun onInitView() {
        if (listTranslateEntity.isEmpty())
            onReadListRoomTranslateEntity()
        else
            translateLiveData.postValue(AppState.Success(listTranslateEntity))
    }

    protected open suspend fun startLoadingData() {
        withContext(Dispatchers.IO) {
            translateHistoryInteractor.readListRoomTranslateEntity().map {
                mapFromRoomTranslateEntityToTranslateEntity(it)
            }.let {
                if (it.isEmpty())
                    onEmptyData(emptiData)
                else
                    onCorrectData(it)
            }
        }
    }

}