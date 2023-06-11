package com.example.translate.view_model.translate_view_model

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.core.interactor.IChangingFavoritesStateInteractor
import com.example.translate.interactor.translate_interactor.ITranslateInteractor
import com.example.model.data.dto.DataModel
import com.example.utils.networkstate.INetworkStatus

class TranslateSavedStateViewModelFactory(
    private val changingFavoritesStateInteractor: IChangingFavoritesStateInteractor,
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
    ): T = TranslateViewModel(changingFavoritesStateInteractor,translateInteractor, networkStatus, handle) as T

}