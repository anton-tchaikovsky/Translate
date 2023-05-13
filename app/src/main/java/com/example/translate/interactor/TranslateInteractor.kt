package com.example.translate.interactor

import com.example.translate.model.data.AppState
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.repository.IRepository
import com.example.translate.unit.mapFromDataModelItemToTranslateEntity
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TranslateInteractor @Inject constructor (private val repository: IRepository<DataModel>) :
    ITranslateInteractor<AppState> {

    override fun getDataModel(text: String): Single<AppState> =
        repository.getConnectState()
            .flatMap { isConnect ->
                if (isConnect)
                    return@flatMap subscribeToLoadingDataModel(text)
                else
                    return@flatMap Single.fromCallable {
                        return@fromCallable AppState.Info(DISCONNECT_NETWORK)
                    }
            }

    private fun subscribeToLoadingDataModel(text: String) =
        repository.getDataModel(text).map { dataModel ->
            AppState.Success(dataModel.map {
                mapFromDataModelItemToTranslateEntity(it)
            })
        }

    companion object {
        private const val DISCONNECT_NETWORK = "Отсутствует подключение к сети"
    }

}