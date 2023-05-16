package com.example.translate.model.repository

import android.net.ConnectivityManager.NetworkCallback
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_source.IDataSource
import com.example.translate.model.network.INetworkStatus

class Repository(
    private val dataSource: IDataSource<DataModel>,
    private val netWorkStatus: INetworkStatus
) : IRepository<DataModel> {

    override suspend fun getDataModel(text: String): DataModel =
        dataSource.getDataModelAsync(text)

    override fun registerNetworkCallback(networkCallback: NetworkCallback) {
        netWorkStatus.registerNetworkCallback(networkCallback)
    }

    override fun unregisterNetworkCallback(networkCallback: NetworkCallback) {
        netWorkStatus.unregisterNetworkCallback(networkCallback)
    }

}