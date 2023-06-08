package com.example.core.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.interactor.IChangingFavoritesStateInteractor
import com.example.core.utils.Mapper.mapFromTranslateEntityToRoomTranslateEntity
import com.example.model.data.TranslateEntity
import com.example.model.data.app_state.AppState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseTranslateViewModel(private val changingFavoritesStateInteractor: IChangingFavoritesStateInteractor): ViewModel(), IDataLoader, IChangingFavoritesState {

    protected val translateLiveData = MutableLiveData<AppState>()

    protected val singleEventLiveData = SingleEventLiveData<AppState>()

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    private val  listTranslateEntity: MutableList<TranslateEntity> = mutableListOf()

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    override fun onLoadingData() {
        translateLiveData.value = AppState.Loading
    }

    override fun onErrorLoadingData(error: Throwable) {
        this.listTranslateEntity.clear()
        singleEventLiveData.postValue(AppState.Error(error))
        translateLiveData.postValue(AppState.EmptyData)
    }

    override fun onEmptyData(info: String) {
        this.listTranslateEntity.clear()
        singleEventLiveData.postValue(AppState.Info(info))
        translateLiveData.postValue(AppState.EmptyData)
    }

    override fun onCorrectData(listTranslateEntity: List<TranslateEntity>) {
        this.listTranslateEntity.apply {
            clear()
            addAll(listTranslateEntity)
        }
        translateLiveData.postValue(AppState.Success(this.listTranslateEntity))
    }

    override fun onChangingFavoritesState(translateEntity: TranslateEntity) {
        val updatePosition = listTranslateEntity.indexOf(translateEntity)
        val updateTranslateEntity = translateEntity.apply {
            isFavorites = !isFavorites
        }
        viewModelCoroutineScope.launch {
            withContext(Dispatchers.IO) {
                listTranslateEntity[updatePosition] = updateTranslateEntity
                changingFavoritesStateInteractor.updateRoomTranslateEntity(
                    mapFromTranslateEntityToRoomTranslateEntity(
                        listTranslateEntity[updatePosition]
                    )
                )
            }
            translateLiveData.postValue(
                AppState.SuccessChangeFavorites(
                    updatePosition,
                    listTranslateEntity
                )
            )
        }
    }

    private fun cancelJob(){
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun onInitView()

    fun getTranslateLiveData(): LiveData<AppState> = translateLiveData

    fun getSingleEventLiveData(): LiveData<AppState> = singleEventLiveData

    private fun handleError (error: Throwable){
        onErrorLoadingData(error)
    }

}