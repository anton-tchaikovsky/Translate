package com.example.translate.di.modules

import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.AppState
import com.example.translate.view_model.view_model_factory.TranslateViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TranslateViewModelFactoryModule {

    @Provides
    fun translateViewModelFactory(translateInteractor: ITranslateInteractor<AppState>): TranslateViewModelFactory =
        TranslateViewModelFactory(translateInteractor)

}