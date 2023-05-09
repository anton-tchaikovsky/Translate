package com.example.translate.di.modules

import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_source.IDataSource
import com.example.translate.model.repository.IRepository
import com.example.translate.model.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun repository(remoteDataSource: IDataSource<DataModel>):IRepository<DataModel> = Repository(remoteDataSource)

}