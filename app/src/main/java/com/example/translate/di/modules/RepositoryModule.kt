package com.example.translate.di.modules

import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.network.INetWorkStatus
import com.example.translate.model.network.NetWorkStatus
import com.example.translate.model.repository.IRepository
import com.example.translate.model.repository.Repository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun repository(repository: Repository): IRepository<DataModel>

    @Singleton
    @Binds
    fun netWorkStatus(netWorkStatus: NetWorkStatus): INetWorkStatus

}