package com.example.translate.presenter

import com.example.translate.model.data.AppState
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.repository.IRepository
import io.reactivex.rxjava3.core.Single

class MainInteractor(private val repository: IRepository<DataModel>): IMainInteractor<AppState> {

    override fun getDataModel(word: String): Single<AppState> =
        repository.getDataModel(word).map {
            AppState.Success(it)
        }

}