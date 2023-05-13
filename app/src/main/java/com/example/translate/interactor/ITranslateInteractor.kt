package com.example.translate.interactor

import com.example.translate.model.data.AppState
import io.reactivex.rxjava3.core.Single

interface ITranslateInteractor<T: AppState> {

    fun getDataModel(text: String): Single<T>

}