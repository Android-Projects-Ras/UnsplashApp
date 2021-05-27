package com.example.myapp.components

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myapp.R

class SoundVibroService() : IntentService("name") {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrationEffect: VibrationEffect
    private lateinit var vibrator: Vibrator
    private lateinit var serviceCallback: ServiceCallback
    private val TAG = "SoundVibroService"

    //1.simpleCallback 2. withCallback

    /*fun callback(simpleCallback: () -> Unit) {
        Toast.makeText(this, "withCallback", Toast.LENGTH_SHORT).show()
        simpleCallback() // todo: типа такого надо?
    }*/

    //val soundVibroServiceBinder = SoundVibroServiceBinder()



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onHandleIntent(intent: Intent?) {
        Thread.sleep(3000)

        mediaPlayer = MediaPlayer.create(this, R.raw.win_sound)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        mediaPlayer.start()
        vibrationEffect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(vibrationEffect)
        //Toast.makeText(this, "Service works", Toast.LENGTH_SHORT).show()
        serviceCallback.doSomething()

    }

    /** method for clients  */
    fun getTextForToast() = "SoundVibroService done"

    inner class SoundVibroServiceBinder() : Binder() {
        fun getSoundVibroService(): SoundVibroService = this@SoundVibroService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return SoundVibroServiceBinder()
    }

    fun setCallbacks(callbacks: ServiceCallback?) {
        if (callbacks != null) {
            serviceCallback = callbacks
        }
    }
}

interface ServiceCallback {
    fun doSomething()
}