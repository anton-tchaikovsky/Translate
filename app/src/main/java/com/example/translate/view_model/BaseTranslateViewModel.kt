package com.example.translate.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.translate.model.data.TranslateEntity
import com.example.translate.model.data.app_state.AppState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

abstract class BaseTranslateViewModel: ViewModel(), IDataLoader<TranslateEntity> {

    protected val translateLiveData = MutableLiveData<AppState>()

    protected val singleEventLiveData = SingleEventLiveData<AppState>()

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    override fun onLoadingData() {
        translateLiveData.value = AppState.Loading
    }

    override fun onErrorLoadingData(error: Throwable) {
        singleEventLiveData.postValue(AppState.Error(error))
        translateLiveData.postValue(AppState.EmptyData)
    }

    override fun onEmptyData(info: String) {
        singleEventLiveData.postValue(AppState.Info(info))
        translateLiveData.postValue(AppState.EmptyData)
    }

    override fun onCorrectData(listTranslateEntity: List<TranslateEntity>) {
        translateLiveData.postValue(AppState.Success(listTranslateEntity))
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