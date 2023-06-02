package com.example.translate.di_koin

import androidx.room.Room
import androidx.savedstate.SavedStateRegistryOwner
import com.example.translate.image_loader.IImageLoader
import com.example.translate.image_loader.PicassoImageLoader
import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.interactor.TranslateInteractor
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_source.local_data_source.ILocalDataSource
import com.example.translate.model.data_source.local_data_source.LocalDataSource
import com.example.translate.model.data_source.remote_data_source.IRemoteDataSource
import com.example.translate.model.data_source.remote_data_source.api.RemoteDataSource
import com.example.translate.model.network.INetworkStatus
import com.example.translate.model.network.NetworkStatus
import com.example.translate.model.repository.IRepository
import com.example.translate.model.repository.Repository
import com.example.translate.model.room.RoomTranslateDB
import com.example.translate.view_model.view_model_factory.TranslateHistoryViewModelFactory
import com.example.translate.view_model.view_model_factory.TranslateSavedStateViewModelFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repositoryModule = module {
    single<IRemoteDataSource<DataModel>> {
        RemoteDataSource()
    }

    single<ILocalDataSource> {
        LocalDataSource(roomTranslateDAO = get())
    }

    single<INetworkStatus> {
        NetworkStatus(context = androidApplication())
    }
    single<IRepository<DataModel>> {
        Repository(remoteDataSource = get(), localDataSource = get(), netWorkStatus = get())
    }
}

val roomDatabaseModule = module {
    single {
        Room.databaseBuilder(
            context = androidApplication(),
            RoomTranslateDB::class.java,
            "translate_database"
        ).build()
    }
    single { get<RoomTranslateDB>().getRoomDAO() }
}

val translateModule = module {
    factory<ITranslateInteractor<DataModel>> {
        TranslateInteractor(repository = get())
    }
    factory { (owner: SavedStateRegistryOwner) ->
        TranslateSavedStateViewModelFactory(translateInteractor = get(), owner = owner)
    }
    factory {
        TranslateHistoryViewModelFactory(translateInteractor = get())
    }
}

val imageLoaderModule = module {
    single<IImageLoader> {
        PicassoImageLoader(context = androidApplication())
    }
}