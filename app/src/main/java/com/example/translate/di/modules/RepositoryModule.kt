package com.example.translate.di.modules

import android.app.Application
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_source.IDataSource
import com.example.translate.model.network.INetWorkStatus
import com.example.translate.model.network.NetWorkStatus
import com.example.translate.model.repository.IRepository
import com.example.translate.model.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun repository(
        remoteDataSource: IDataSource<DataModel>,
        netWorkStatus: INetWorkStatus
    ): IRepository<DataModel> = Repository(remoteDataSource, netWorkStatus)

    @Singleton
    @Provides
    fun netWorkStatus(application: Application): INetWorkStatus = NetWorkStatus(application)

}