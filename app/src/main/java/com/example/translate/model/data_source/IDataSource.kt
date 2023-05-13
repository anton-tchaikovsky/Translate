package com.example.translate.model.data_source

import io.reactivex.rxjava3.core.Single

interface IDataSource <T: Any> {

    fun getDataModel(text: String): Single<T>

}