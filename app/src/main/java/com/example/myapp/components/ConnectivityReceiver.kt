package com.example.myapp.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myapp.ui.NetworkStatusListener

class ConnectivityReceiver() : BroadcastReceiver() {
    private val networkListener by lazy {
        NetworkStatusListener()
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onReceive(context: Context?, intent: Intent?) {
        networkListener.register(context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val isConnected = networkListener.isConnected
            if (isConnected) {
                Toast.makeText(context, "Connected!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Not connected!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}