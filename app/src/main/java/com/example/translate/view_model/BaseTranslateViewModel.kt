package com.example.translate.view_model

import android.util.Log
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

abstract class BaseTranslateViewModel: ViewModel(), IDataLoader {

    protected val translateLiveData = MutableLiveData<AppState>()

    protected val singleEventLiveData = SingleEventLiveData<AppState>()

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    protected val  listTranslateEntity: MutableList<TranslateEntity> = mutableListOf()

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

    private fun cancelJob(){
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun onInitView()

    fun getTranslateLiveData(): LiveData<AppState> = translateLiveData

    fun getSingleEventLiveData(): LiveData<AppState> = singleEventLiveData

    private fun handleError (error: Throwable){
        onErrorLoadingData(error)
        Log.d("@@@", error.message.toString())
    }

}