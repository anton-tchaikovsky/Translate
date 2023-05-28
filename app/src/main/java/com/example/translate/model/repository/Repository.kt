package com.example.translate.model.repository

import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_source.IDataSource
import com.example.translate.model.network.INetworkStatus
import kotlinx.coroutines.flow.StateFlow

class Repository(
    private val dataSource: IDataSource<DataModel>,
    private val netWorkStatus: INetworkStatus
) : IRepository<DataModel> {

    override suspend fun getDataModel(text: String): DataModel =
        dataSource.getDataModelAsync(text)

    override fun registerNetworkCallback(): StateFlow<Boolean> =
        netWorkStatus.registerNetworkCallback()

    override fun unregisterNetworkCallback() {
        netWorkStatus.unregisterNetworkCallback()
    }

}