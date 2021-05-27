package com.example.myapp.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class ConnectivityReceiver(
    private val receiverListener: (String) -> Unit
) : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "ACTION_CONNECTION_CHANGE") {
            val isConnected = intent.getBooleanExtra("isConnected", false)
            if (isConnected) {
                receiverListener("Connected!")
            } else {
                receiverListener("Not Connected!")
            }
        }
    }
}