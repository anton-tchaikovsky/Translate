package com.example.translate.model.data_sourse.api

import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_sourse.IDataSourse
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSourse: IDataSourse<DataModel> {

    override fun getDataModel(text: String): Single<DataModel> = getServiceApiSkyeng().search(text)

    private fun getServiceApiSkyeng(): IApiSkyeng = createRetrofitApiSkyeng().create(IApiSkyeng::class.java)

    private fun createRetrofitApiSkyeng(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_API_SKYENG)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    companion object{
        private const val BASE_URL_API_SKYENG = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}