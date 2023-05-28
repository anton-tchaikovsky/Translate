package com.example.translate.model.network

import kotlinx.coroutines.flow.StateFlow

interface INetworkStatus {

    fun registerNetworkCallback():StateFlow<Boolean>

    fun unregisterNetworkCallback()

}