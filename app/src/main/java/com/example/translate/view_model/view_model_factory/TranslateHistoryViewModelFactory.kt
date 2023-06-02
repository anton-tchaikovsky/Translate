package com.example.translate.view_model.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.dto.DataModel
import com.example.translate.view_model.translate_history_view_model.TranslateHistoryViewModel

class TranslateHistoryViewModelFactory(
    private val translateInteractor: ITranslateInteractor<DataModel>,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TranslateHistoryViewModel(translateInteractor) as T

}