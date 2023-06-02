package com.example.translate.model.data_source.remote_data_source

import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource <T> {

    suspend fun getDataModel(text: String): Flow<T>

}