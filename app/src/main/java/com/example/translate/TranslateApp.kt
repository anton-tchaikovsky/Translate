package com.example.translate

import android.app.Application
import com.example.translate.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TranslateApp:Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        createAppComponent()
    }

    private fun createAppComponent() =
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}