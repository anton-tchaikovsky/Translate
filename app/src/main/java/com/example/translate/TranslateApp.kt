package com.example.translate

import android.app.Application
import com.example.translate.di_koin.imageLoaderModule
import com.example.translate.di_koin.repositoryModule
import com.example.translate.di_koin.translateModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TranslateApp:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TranslateApp)
            modules(listOf(translateModule, repositoryModule, imageLoaderModule))
        }
    }

}