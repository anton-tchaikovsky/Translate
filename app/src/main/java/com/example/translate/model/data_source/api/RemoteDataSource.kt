package com.example.translate.model.data_source.api

import android.util.Log
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_source.IDataSource
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource : IDataSource<DataModel> {

    private val interceptor = HttpLoggingInterceptor {
        Log.d(LOG_TAG_OK_HTTP, it)
    }.apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    override suspend fun getDataModel(text: String): Flow<DataModel> = flow {
        emit(getServiceApiSkyeng().search(text))
    }.flowOn(Dispatchers.IO)

    private fun getServiceApiSkyeng(): IApiSkyeng =
        createRetrofitApiSkyeng(interceptor).create(IApiSkyeng::class.java)

    private fun createRetrofitApiSkyeng(intercepter: Interceptor): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_API_SKYENG)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
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