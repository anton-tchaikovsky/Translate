package com.example.translate.model.repository

import kotlinx.coroutines.flow.StateFlow

interface IRepository <T: Any> {

    suspend fun getDataModel(text: String): T

    fun registerNetworkCallback(): StateFlow<Boolean>

    fun unregisterNetworkCallback()

}