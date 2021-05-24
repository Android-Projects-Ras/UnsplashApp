package com.example.myapp.ui

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.components.ConnectivityReceiver
import com.example.myapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var connectivityReceiver: ConnectivityReceiver
    private val networkListener by lazy {
        NetworkStatusListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_graph) as NavHostFragment
        navController = navHostFragment.findNavController()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()
        connectivityReceiver = ConnectivityReceiver()
        networkListener.register(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(connectivityReceiver)
        networkListener.unregister(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    }
}

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