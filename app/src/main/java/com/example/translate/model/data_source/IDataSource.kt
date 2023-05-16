package com.example.translate.model.data_source

interface IDataSource <T> {

    suspend fun getDataModelAsync(text: String): T

}