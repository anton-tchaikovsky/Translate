package com.example.translate.model.repository

import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_sourse.IDataSourse
import io.reactivex.rxjava3.core.Single

class Repository(private val dataSourse: IDataSourse<DataModel>) : IRepository<DataModel> {

    override fun getDataModel(text: String): Single<DataModel> =
        dataSourse.getDataModel(text)

}