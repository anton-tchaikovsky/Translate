package com.example.translate.model.data_source

import kotlinx.coroutines.flow.Flow

interface IDataSource <T> {

    suspend fun getDataModel(text: String): Flow<T>

}