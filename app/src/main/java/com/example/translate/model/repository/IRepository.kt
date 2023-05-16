package com.example.translate.model.repository

import android.net.ConnectivityManager.NetworkCallback

interface IRepository <T: Any> {

    suspend fun getDataModel(text: String): T

    fun registerNetworkCallback(networkCallback: NetworkCallback)

    fun unregisterNetworkCallback(networkCallback: NetworkCallback)

}