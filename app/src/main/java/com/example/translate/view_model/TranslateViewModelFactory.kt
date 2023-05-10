package com.example.translate.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.AppState
import javax.inject.Inject

class TranslateViewModelFactory @Inject constructor(private val translateInteractor: ITranslateInteractor<AppState>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(TranslateViewModel::class.java))
            return TranslateViewModel(translateInteractor) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
