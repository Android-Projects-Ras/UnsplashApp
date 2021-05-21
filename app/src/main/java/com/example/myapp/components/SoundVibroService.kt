package com.example.myapp.components

import android.app.Service
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

class SoundVibroService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrationEffect: VibrationEffect
    private lateinit var vibrator: Vibrator

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        mediaPlayer = MediaPlayer.create(this, R.raw.win_sound)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        Toast.makeText(this, "SoundVibroService created", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "SoundVibroService started", Toast.LENGTH_SHORT).show()
        mediaPlayer.start()
        vibrationEffect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(vibrationEffect)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "SoundVibroService destroyed", Toast.LENGTH_SHORT).show()
    }
}