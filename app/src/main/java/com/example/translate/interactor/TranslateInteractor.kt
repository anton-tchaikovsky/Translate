package com.example.translate.interactor

import android.net.ConnectivityManager.NetworkCallback
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.repository.IRepository

class TranslateInteractor(private val repository: IRepository<DataModel>) :
    ITranslateInteractor<DataModel> {

    override suspend fun getDataModel(text: String): DataModel = repository.getDataModel(text)

    override fun registerNetworkCallback(networkCallback: NetworkCallback) {
        repository.registerNetworkCallback(networkCallback)
    }

    override fun unregisterNetworkCallback(networkCallback: NetworkCallback) {
        repository.unregisterNetworkCallback(networkCallback)
    }

}