package com.example.translate.di_koin

import androidx.savedstate.SavedStateRegistryOwner
import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.interactor.TranslateInteractor
import com.example.translate.model.data.AppState
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_source.IDataSource
import com.example.translate.model.data_source.api.RemoteDataSource
import com.example.translate.model.network.INetWorkStatus
import com.example.translate.model.network.NetWorkStatus
import com.example.translate.model.repository.IRepository
import com.example.translate.model.repository.Repository
import com.example.translate.view_model.view_model_factory.TranslateSavedStateViewModelFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repositoryModule = module {
    single<IDataSource<DataModel>> {
        RemoteDataSource()
    }
    single<INetWorkStatus> {
        NetWorkStatus(context = androidApplication())
    }
    single<IRepository<DataModel>> {
        Repository(dataSource = get(), netWorkStatus = get())
    }
}

val translateModule = module {
    factory<ITranslateInteractor<AppState>> {
        TranslateInteractor(repository = get())
    }
    factory { (owner: SavedStateRegistryOwner) ->
        TranslateSavedStateViewModelFactory(translateInteractor = get(), owner = owner)
    }
}