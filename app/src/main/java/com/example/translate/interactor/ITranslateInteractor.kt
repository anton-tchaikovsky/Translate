package com.example.translate.interactor

import android.net.ConnectivityManager.NetworkCallback

interface ITranslateInteractor<T> {

    suspend fun getDataModel(text: String): T

    fun registerNetworkCallback(networkCallback: NetworkCallback)

    fun unregisterNetworkCallback(networkCallback: NetworkCallback)

}