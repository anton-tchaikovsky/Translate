package com.example.translate.di.modules

import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_source.IDataSource
import com.example.translate.model.data_source.api.RemoteDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RemoteDataSourceModule {

    @Singleton
    @Binds
    fun remoteDataSource(remoteDataSource: RemoteDataSource): IDataSource<DataModel>

}