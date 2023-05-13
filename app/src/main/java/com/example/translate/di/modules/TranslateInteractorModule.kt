package com.example.translate.di.modules

import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.interactor.TranslateInteractor
import com.example.translate.model.data.AppState
import dagger.Binds
import dagger.Module

@Module
interface TranslateInteractorModule {

    @Binds
    fun translateInteractor(translateInteractor: TranslateInteractor): ITranslateInteractor<AppState>

}