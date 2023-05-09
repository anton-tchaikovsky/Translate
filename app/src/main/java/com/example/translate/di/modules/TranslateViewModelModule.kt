package com.example.translate.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.translate.model.data.AppState
import com.example.translate.view.BaseTranslateActivity
import com.example.translate.view_model.BaseTranslateViewModel
import com.example.translate.view_model.TranslateViewModel
import dagger.Module
import dagger.Provides

@Module
class TranslateViewModelModule {

    @Provides
    fun translateViewModel(translateActivity: BaseTranslateActivity<AppState>):BaseTranslateViewModel<AppState> =
        ViewModelProvider(translateActivity)[TranslateViewModel::class.java]

}