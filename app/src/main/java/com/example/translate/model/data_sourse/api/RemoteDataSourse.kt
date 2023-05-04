package com.example.translate.model.data_sourse.api

import android.util.Log
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_sourse.IDataSourse
import io.reactivex.rxjava3.core.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSourse : IDataSourse<DataModel> {

    private val interceptor = HttpLoggingInterceptor {
        Log.d(LOG_TAG_OK_HTTP, it)
    }.apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    override fun getDataModel(text: String): Single<DataModel> = getServiceApiSkyeng().search(text)

    private fun getServiceApiSkyeng(): IApiSkyeng =
        createRetrofitApiSkyeng(interceptor).create(IApiSkyeng::class.java)

    private fun createRetrofitApiSkyeng(intercepter: Interceptor): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_API_SKYENG)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(createOkHttpClient(intercepter))
            .build()

    private fun createOkHttpClient(intercepter: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(intercepter)
            .build()

    companion object {
        private const val BASE_URL_API_SKYENG = "https://dictionary.skyeng.ru/api/public/v1/"
        private const val LOG_TAG_OK_HTTP = "OkHttp"
    }
}