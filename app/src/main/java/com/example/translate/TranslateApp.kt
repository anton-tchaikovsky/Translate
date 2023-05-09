package com.example.translate

import android.app.Application
import com.example.translate.di.AppComponent
import com.example.translate.di.DaggerAppComponent

class TranslateApp:Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = createAppComponent()
    }

    private fun createAppComponent(): AppComponent =
        DaggerAppComponent.builder()
            .application(this)
            .build()

    companion object{
        lateinit var appComponent: AppComponent
    }

}