package com.example.translate.view_model.view_model_factory

import androidx.lifecycle.SavedStateHandle
import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.AppState
import com.example.translate.view_model.TranslateViewModel

class TranslateViewModelFactory(
    private val translteInteractor: ITranslateInteractor<AppState>
) : IViewModelFactory<TranslateViewModel> {

    override fun create(handle: SavedStateHandle): TranslateViewModel =
        TranslateViewModel(translteInteractor/*, handle*/)

}