package com.example.translate.interactor

import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.repository.IRepository
import kotlinx.coroutines.flow.StateFlow

class TranslateInteractor(private val repository: IRepository<DataModel>) :
    ITranslateInteractor<DataModel> {

    override suspend fun getDataModel(text: String): DataModel = repository.getDataModel(text)

    override fun registerNetworkCallback(): StateFlow<Boolean> =
        repository.registerNetworkCallback()

    override fun unregisterNetworkCallback() {
        repository.unregisterNetworkCallback()
    }

}