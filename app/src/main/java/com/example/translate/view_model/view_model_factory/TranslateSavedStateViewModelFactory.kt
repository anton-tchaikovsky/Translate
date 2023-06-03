package com.example.translate.view_model.view_model_factory

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.dto.DataModel
import com.example.translate.view_model.translate_view_model.TranslateViewModel

class TranslateSavedStateViewModelFactory(
    private val translateInteractor: ITranslateInteractor<DataModel>,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = TranslateViewModel(translateInteractor, handle) as T

}