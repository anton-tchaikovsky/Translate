package com.example.translate.presenter

import com.example.translate.model.data.AppState
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.repository.IRepository
import io.reactivex.rxjava3.core.Single

class TranslateInteractor(private val repository: IRepository<DataModel>): ITranslateInteractor<AppState> {

    override fun getDataModel(text: String): Single<AppState> =
        repository.getDataModel(text).map {
            AppState.Success(it)
        }

}