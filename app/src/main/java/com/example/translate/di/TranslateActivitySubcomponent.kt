package com.example.translate.di

import com.example.translate.di.modules.TranslateViewModelModule
import com.example.translate.model.data.AppState
import com.example.translate.view.BaseTranslateActivity
import com.example.translate.view.TranslateActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules =
    [
        TranslateViewModelModule::class
    ]
)
interface TranslateActivitySubcomponent {

    @Subcomponent.Builder
    interface Builder{

        @BindsInstance
        fun translateActivity(translateActivity: BaseTranslateActivity<AppState>): Builder

        fun build():TranslateActivitySubcomponent

    }

    fun inject(translateActivity: TranslateActivity)

}