package com.example.translate.presenter

import com.example.translate.model.data.AppState
import io.reactivex.rxjava3.core.Single

interface IMainInteractor<T: AppState> {

    fun getDataModel(word: String): Single<T>

}