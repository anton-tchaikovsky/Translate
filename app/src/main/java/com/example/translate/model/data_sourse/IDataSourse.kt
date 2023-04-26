package com.example.translate.model.data_sourse

import io.reactivex.rxjava3.core.Single

interface IDataSourse <T: Any> {

    fun getDataModel(word: String): Single<T>

}