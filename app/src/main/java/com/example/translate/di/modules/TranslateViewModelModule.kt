package com.example.translate.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.translate.view_model.BaseViewModelFactory
import com.example.translate.view_model.TranslateViewModel
import com.example.translate.view_model.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module (includes = [TranslateInteractorModule::class])
internal abstract class TranslateViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: BaseViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TranslateViewModel::class)
    abstract fun translateViewModel(translateViewModel: TranslateViewModel): ViewModel

}