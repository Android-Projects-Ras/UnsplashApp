package com.example.myapp.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi

class ConnectivityReceiver(
    private val receiverListener: (String) -> Unit
) : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "ACT_LOC") {
            val isConnected = intent.getBooleanExtra("isConnected", false)
            if (isConnected) {
                //todo: ?
                receiverListener("Connected!")
                Toast.makeText(context, "Connected!", Toast.LENGTH_SHORT).show()
            } else {
                receiverListener("Not connected!")
                Toast.makeText(context, "Not connected!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}