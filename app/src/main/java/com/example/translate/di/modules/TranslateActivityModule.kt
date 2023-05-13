package com.example.translate.di.modules

import com.example.translate.view.TranslateActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface TranslateActivityModule {

    @ContributesAndroidInjector
    fun contributeTranslateActivity(): TranslateActivity

}