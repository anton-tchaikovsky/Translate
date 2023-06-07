package com.example.translate.view_model.view_model_factory

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.core.interactor.ITranslateInteractor
import com.example.model.data.dto.DataModel
import com.example.utils.networkstate.INetworkStatus
import com.example.translate.view_model.translate_view_model.TranslateViewModel

class TranslateSavedStateViewModelFactory(
    private val translateInteractor: ITranslateInteractor<DataModel>,
    private val networkStatus: INetworkStatus,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = TranslateViewModel(translateInteractor, networkStatus, handle) as T

}