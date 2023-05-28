package com.example.translate.interactor

import kotlinx.coroutines.flow.StateFlow

interface ITranslateInteractor<T> {

    suspend fun getDataModel(text: String): T

    fun registerNetworkCallback(): StateFlow<Boolean>

    fun unregisterNetworkCallback()

}