package com.example.myapp.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi

class ConnectivityReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                //take action when network connection is gained
                val toast = Toast.makeText(context, "Internet available", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 250, 250)
                toast.show()
                    Log.d("RogokConReceiver", "onAvailable: ")

            }

            override fun onLost(network: Network) {
                //take action when network connection is lost
                val toast = Toast.makeText(context, "Internet unavailable", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 250, 250)
                toast.show()
                    Log.d("RogokConReceiver", "onUnavailable: ")

            }
        })

        /*val noConnectivity =
            intent?.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false) ?: return
        if (noConnectivity) {
            Toast.makeText(context, "Internet unavailable", Toast.LENGTH_SHORT).apply {
                setGravity(Gravity.TOP*//* or Gravity.CENTER_HORIZONTAL*//*, 0, 0)
                show()
                Log.d("RogokConReceiver", "onAvailable: ")
            }
        } else {
            Toast.makeText(context, "Internet available", Toast.LENGTH_SHORT).apply {
                setGravity(Gravity.TOP, 0, 0)
                show()
                Log.d("RogokConReceiver", "onUnavailable: ")
            }
        }*/
    }
}