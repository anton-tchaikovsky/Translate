package com.example.utils.networkstate

import kotlinx.coroutines.flow.StateFlow

interface INetworkStatus {

    fun registerNetworkCallback():StateFlow<Boolean>

    fun unregisterNetworkCallback()

}