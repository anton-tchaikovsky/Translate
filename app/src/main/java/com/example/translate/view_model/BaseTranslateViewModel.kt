package com.example.translate.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.translate.model.data.AppState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

abstract class BaseTranslateViewModel<T : AppState>: ViewModel() {

    protected abstract var isOnline: Boolean

    protected val translateLiveData = MutableLiveData<T>()

    protected val singleEventLiveData = SingleEventLiveData<T>()

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

    protected fun cancelJob(){
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun onSearchWord(text: String?)

    abstract fun onInitView()

    abstract fun getTranslateLiveData(): LiveData<T>

    abstract fun getSingleEventLiveData(): LiveData<T>

    abstract fun handleError (error: Throwable)


}