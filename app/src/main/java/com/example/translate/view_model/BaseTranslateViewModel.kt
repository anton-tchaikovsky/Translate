package com.example.translate.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.translate.model.data.AppState

abstract class BaseTranslateViewModel<T: AppState>(
   protected val translateLiveData: MutableLiveData<T> = MutableLiveData(),
) : ViewModel() {

    abstract fun onSearchWord(text: String?)

    abstract fun getTranslateLiveData():LiveData<T>

}