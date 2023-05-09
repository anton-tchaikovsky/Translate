package com.example.translate.di.modules

import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_source.IDataSource
import com.example.translate.model.data_source.api.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun remoteDataSource(): IDataSource<DataModel> = RemoteDataSource()

}