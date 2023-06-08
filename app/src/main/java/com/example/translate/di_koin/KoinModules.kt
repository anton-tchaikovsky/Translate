package com.example.translate.di_koin

import androidx.room.Room
import androidx.savedstate.SavedStateRegistryOwner
import com.example.core.interactor.ChangingFavoritesStateInteractor
import com.example.core.interactor.IChangingFavoritesStateInteractor
import com.example.model.data.dto.DataModel
import com.example.repository.data_source.local_data_source.ILocalDataSource
import com.example.repository.data_source.local_data_source.LocalDataSource
import com.example.repository.data_source.remote_data_source.IRemoteDataSource
import com.example.repository.data_source.remote_data_source.api.RemoteDataSource
import com.example.repository.repository.IRepository
import com.example.repository.repository.Repository
import com.example.repository.room.RoomTranslateDB
import com.example.translate.interactor.translate_favorites_interactor.TranslateFavoritesInteractor
import com.example.translate.interactor.translate_history_interactor.TranslateHistoryInteractor
import com.example.translate.interactor.translate_interactor.TranslateInteractor
import com.example.translate.view.TranslateActivity
import com.example.translate.view.TranslateFavoritesActivity
import com.example.translate.view.TranslateHistoryActivity
import com.example.translate.view_model.translate_favorites_view_model.TranslateFavoritesViewModel
import com.example.translate.view_model.translate_history_view_model.TranslateHistoryViewModel
import com.example.translate.view_model.translate_view_model.TranslateSavedStateViewModelFactory
import com.example.translate_foto_screen.TranslateFotoActivity
import com.example.utils.image_loader.CoilImageLoader
import com.example.utils.image_loader.IImageLoader
import com.example.utils.networkstate.NetworkStatus
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
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

val activityModule = module{

    single<IChangingFavoritesStateInteractor> {
        ChangingFavoritesStateInteractor(repository = get())
    }

    scope(named<TranslateActivity>()){
        scoped {  (owner: SavedStateRegistryOwner) ->
            TranslateSavedStateViewModelFactory(
                changingFavoritesStateInteractor = get(),
                translateInteractor = TranslateInteractor(repository = get()),
                networkStatus = NetworkStatus(context = androidApplication()),
                owner = owner
            ) }
    }

    scope(named<TranslateFotoActivity>()) {
        scoped<IImageLoader> { CoilImageLoader(context = androidApplication()) }
    }

    scope(named<TranslateHistoryActivity>()) {
        viewModel {
            TranslateHistoryViewModel(
                changingFavoritesStateInteractor = get(),
                translateHistoryInteractor = TranslateHistoryInteractor(repository = get())
            )
        }
    }

    scope(named<TranslateFavoritesActivity>()) {
        viewModel {
            TranslateFavoritesViewModel(
                changingFavoritesStateInteractor = get(),
                translateFavoritesInteractor = TranslateFavoritesInteractor(repository = get())
            )
        }
    }
}