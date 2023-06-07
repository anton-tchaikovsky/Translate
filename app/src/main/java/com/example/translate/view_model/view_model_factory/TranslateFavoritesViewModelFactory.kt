package com.example.translate.view_model.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.model.data.dto.DataModel
import com.example.core.interactor.ITranslateInteractor
import com.example.translate.view_model.translate_favorites_view_model.TranslateFavoritesViewModel

class TranslateFavoritesViewModelFactory(
    private val translateInteractor: ITranslateInteractor<DataModel>,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TranslateFavoritesViewModel(translateInteractor) as T

}