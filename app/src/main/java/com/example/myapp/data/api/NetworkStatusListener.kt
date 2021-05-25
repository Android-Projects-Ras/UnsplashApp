package com.example.myapp.data.api

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkStatusListener {

    private var callback: ConnectivityManager.NetworkCallback? = null
    private val isConnectedFlowInternal = MutableStateFlow(false)
    val isConnectedFlow: Flow<Boolean> = isConnectedFlowInternal
    val isConnected: Boolean
        get() = isConnectedFlowInternal.value

    @RequiresApi(Build.VERSION_CODES.N)
    fun register(connectivityManager: ConnectivityManager) {
        callback = object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isConnectedFlowInternal.value = true

            }

            override fun onLost(network: Network) {
                isConnectedFlowInternal.value = false

            }
        }.also {
            connectivityManager.registerDefaultNetworkCallback(it)
        }
    }

    fun unregister(connectivityManager: ConnectivityManager) {
        callback?.also {
            connectivityManager.unregisterNetworkCallback(it)
        }
        callback = null
    }
}