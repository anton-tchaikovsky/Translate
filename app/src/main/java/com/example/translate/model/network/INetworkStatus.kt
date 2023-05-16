package com.example.translate.model.network

import android.net.ConnectivityManager.NetworkCallback

interface INetworkStatus {
    fun registerNetworkCallback(networkCallback: NetworkCallback)

    fun unregisterNetworkCallback(networkCallback: NetworkCallback)

}