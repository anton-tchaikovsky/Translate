package com.example.translate.model.data_sourse

import io.reactivex.rxjava3.core.Single

interface IDataSourse <T: Any> {

    fun getDataModel(text: String): Single<T>

}