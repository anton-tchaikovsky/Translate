package com.example.translate.di.modules

import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.interactor.TranslateInteractor
import com.example.translate.model.data.AppState
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.repository.IRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TranslateInteractorModule {

    @Singleton
    @Provides
    fun translateInteractor(repository: IRepository<DataModel>): ITranslateInteractor<AppState> =
        TranslateInteractor(repository)

}