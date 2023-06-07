package com.example.utils.networkstate

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkStatus (val context: Context) : INetworkStatus {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val stateFlowNetworkCallback = MutableStateFlow(false)

    private val networkCallback: NetworkCallback = object: NetworkCallback(){
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            stateFlowNetworkCallback.value = true
        }

        override fun onUnavailable() {
            super.onUnavailable()
            stateFlowNetworkCallback.value = false
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            stateFlowNetworkCallback.value = false
        }
    }

    override fun registerNetworkCallback(): StateFlow<Boolean> {
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
        return stateFlowNetworkCallback
    }

    override fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

}