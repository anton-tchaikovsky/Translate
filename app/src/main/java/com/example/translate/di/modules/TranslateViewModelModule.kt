package com.example.translate.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.translate.interactor.TranslateInteractor
import com.example.translate.model.data.AppState
import com.example.translate.view.BaseTranslateActivity
import com.example.translate.view_model.BaseTranslateViewModel
import com.example.translate.view_model.TranslateViewModel
import com.example.translate.view_model.TranslateViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TranslateViewModelModule {

    @Provides
    fun translateViewModel(translateActivity: BaseTranslateActivity<AppState>, translateViewModelFactory: TranslateViewModelFactory):BaseTranslateViewModel<AppState> =
        ViewModelProvider(translateActivity, translateViewModelFactory)[TranslateViewModel::class.java]

    @Provides
    fun translateViewModelFactory(translateInteractor: TranslateInteractor): TranslateViewModelFactory =
        TranslateViewModelFactory(translateInteractor)

}