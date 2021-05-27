package com.example.myapp.data.api

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myapp.components.SoundVibroService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkStatusListener(private val context: Context) {

    private var callback: ConnectivityManager.NetworkCallback? = null
    private val isConnectedFlowInternal = MutableStateFlow(false)
    val isConnectedFlow: Flow<Boolean> = isConnectedFlowInternal
    val isConnected: Boolean
        get() = isConnectedFlowInternal.value
    val broadcastIntent = Intent("ACTION_CONNECTION_CHANGE")
    val serviceIntent = Intent(context, SoundVibroService::class.java)

    @RequiresApi(Build.VERSION_CODES.N)
    fun register(connectivityManager: ConnectivityManager) {
        callback = object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isConnectedFlowInternal.value = true
                broadcastIntent.putExtra("isConnected", true)
                context.sendBroadcast(broadcastIntent)
                context.startService(serviceIntent)
            }

            override fun onLost(network: Network) {
                isConnectedFlowInternal.value = false
                broadcastIntent.putExtra("isConnected", false)
                context.sendBroadcast(broadcastIntent)

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