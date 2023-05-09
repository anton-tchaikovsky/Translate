package com.example.translate.di

import android.app.Application
import com.example.translate.di.modules.RemoteDataSourceModule
import com.example.translate.di.modules.RepositoryModule
import com.example.translate.di.modules.TranslateInteractorModule
import com.example.translate.view_model.TranslateViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepositoryModule::class,
        TranslateInteractorModule::class,
        RemoteDataSourceModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application (application: Application): Builder

        fun build(): AppComponent

    }

    fun inject (translateViewModel: TranslateViewModel)

    fun translateActivitySubcomponentBuilder(): TranslateActivitySubcomponent.Builder

}