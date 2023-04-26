package com.example.translate.model.repository

import io.reactivex.rxjava3.core.Single

interface IRepository <T: Any> {

    fun getDataModel(word: String): Single<T>

}