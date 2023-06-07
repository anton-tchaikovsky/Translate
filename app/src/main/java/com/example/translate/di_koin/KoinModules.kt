package com.example.translate.di_koin

import androidx.room.Room
import androidx.savedstate.SavedStateRegistryOwner
import com.example.model.data.dto.DataModel
import com.example.repository.data_source.local_data_source.ILocalDataSource
import com.example.repository.data_source.local_data_source.LocalDataSource
import com.example.repository.data_source.remote_data_source.IRemoteDataSource
import com.example.repository.data_source.remote_data_source.api.RemoteDataSource
import com.example.repository.repository.IRepository
import com.example.repository.repository.Repository
import com.example.repository.room.RoomTranslateDB
import com.example.utils.image_loader.CoilImageLoader
import com.example.utils.image_loader.IImageLoader
import com.example.core.interactor.ITranslateInteractor
import com.example.core.interactor.TranslateInteractor
import com.example.utils.networkstate.INetworkStatus
import com.example.utils.networkstate.NetworkStatus
import com.example.translate.view_model.view_model_factory.TranslateFavoritesViewModelFactory
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


    single<IRepository<DataModel>> {
        Repository(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }
}

val roomDatabaseModule = module {
    single {
        Room.databaseBuilder(
            context = androidApplication(),
            RoomTranslateDB::class.java,
            "translate_database"
        )
            .addMigrations(RoomTranslateDB.MIGRATION_1_2)
            .build()
    }
    single { get<RoomTranslateDB>().getRoomDAO() }
}

val translateModule = module {
    single <ITranslateInteractor<DataModel>> {
        TranslateInteractor(repository = get())
    }
    single<INetworkStatus> {
        NetworkStatus(context = androidApplication())
    }
    factory { (owner: SavedStateRegistryOwner) ->
        TranslateSavedStateViewModelFactory(translateInteractor = get(), networkStatus = get(), owner = owner)
    }
    factory {
        TranslateHistoryViewModelFactory(translateInteractor = get())
    }
    factory {
        TranslateFavoritesViewModelFactory(translateInteractor = get())
    }
}

val imageLoaderModule = module {
    single<IImageLoader> {
        CoilImageLoader(context = androidApplication())
    }
}