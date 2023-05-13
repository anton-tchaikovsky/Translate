package com.example.translate.model.repository

import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_source.IDataSource
import com.example.translate.model.network.INetWorkStatus
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataSource: IDataSource<DataModel>,
    private val netWorkStatus: INetWorkStatus
) : IRepository<DataModel> {

    override fun getDataModel(text: String): Single<DataModel> =
        dataSource.getDataModel(text)

    override fun getConnectState(): Single<Boolean> =
        netWorkStatus.isConnectSingle()

}