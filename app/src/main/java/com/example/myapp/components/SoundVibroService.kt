package com.example.myapp.components

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myapp.R

class SoundVibroService : IntentService("name") {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrationEffect: VibrationEffect
    private lateinit var vibrator: Vibrator

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onHandleIntent(intent: Intent?) {
        Thread.sleep(5000)
        mediaPlayer = MediaPlayer.create(this, R.raw.win_sound)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        Toast.makeText(this, "SoundVibroService done", Toast.LENGTH_SHORT).show()
        mediaPlayer.start()
        vibrationEffect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(vibrationEffect)

    }
}