package com.example.translate.model.network

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface INetWorkStatus {
    fun isConnectSingle(): Single<Boolean>
    fun isConnectObservable(): Observable<Boolean>
}