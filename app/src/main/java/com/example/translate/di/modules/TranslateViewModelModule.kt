package com.example.translate.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.translate.di.ViewModelKey
import com.example.translate.view_model.TranslateViewModel
import com.example.translate.view_model.view_model_factory.BaseViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module (includes = [TranslateInteractorModule::class])
interface TranslateViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: BaseViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TranslateViewModel::class)
    fun translateViewModel(translateViewModel: TranslateViewModel): ViewModel

}