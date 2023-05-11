package com.example.translate.di

import android.app.Application
import com.example.translate.TranslateApp
import com.example.translate.di.modules.RemoteDataSourceModule
import com.example.translate.di.modules.RepositoryModule
import com.example.translate.di.modules.TranslateActivityModule
import com.example.translate.di.modules.TranslateInteractorModule
import com.example.translate.di.modules.TranslateViewModelFactoryModule
import com.example.translate.di.modules.TranslateViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepositoryModule::class,
        TranslateInteractorModule::class,
        RemoteDataSourceModule::class,
        TranslateViewModelModule::class,
        TranslateViewModelFactoryModule::class,
        TranslateActivityModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application (application: Application): Builder

        fun build(): AppComponent

    }
    
    fun inject (translateApp: TranslateApp)

}