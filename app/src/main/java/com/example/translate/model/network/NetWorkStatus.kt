package com.example.translate.model.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class NetWorkStatus @Inject constructor (context: Context) : INetWorkStatus {

    private val statusSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.create()

    init {
        statusSubject.onNext(false)
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, object :
            ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                statusSubject.onNext(true)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                statusSubject.onNext(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                statusSubject.onNext(false)
            }
        })

    }

    override fun isConnectSingle(): Single<Boolean> =
        statusSubject.first(false)

    override fun isConnectObservable(): Observable<Boolean> =
        statusSubject

}