package com.example.translate.model.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.NetworkRequest

class NetworkStatus (val context: Context) : INetworkStatus {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun registerNetworkCallback(networkCallback: NetworkCallback) {
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    override fun unregisterNetworkCallback(networkCallback: NetworkCallback) {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

}