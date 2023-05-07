package com.example.translate.interactor

import com.example.translate.model.data.AppState
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.repository.IRepository
import com.example.translate.unit.mapFromDataModelItemToTranslateEntity
import io.reactivex.rxjava3.core.Single

class TranslateInteractor(private val repository: IRepository<DataModel>): ITranslateInteractor<AppState> {

    override fun getDataModel(text: String): Single<AppState> =
        repository.getDataModel(text).map {dataModel ->
            AppState.Success(dataModel.map {
                mapFromDataModelItemToTranslateEntity(it)
            })
        }

}