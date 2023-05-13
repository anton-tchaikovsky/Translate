package com.example.translate.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.translate.model.data.AppState

abstract class BaseTranslateViewModel<T : AppState>: ViewModel() {

    protected val translateLiveData = MutableLiveData<T>()

    protected val singleEventLiveData = SingleEventLiveData<T>()

    abstract fun onSearchWord(text: String?)

//    abstract fun onInitView()

    abstract fun getTranslateLiveData(): LiveData<T>

    abstract fun getSingleEventLiveData(): LiveData<T>

}