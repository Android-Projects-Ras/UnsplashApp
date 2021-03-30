package com.example.myapp.data.cache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class InternalCacheImpl : InternalCache {

    override fun loadBitmap(path: String): Bitmap? {
        return try {
            val url = URL(path)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true //read data
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            // Log exception
            e.printStackTrace()
            null
        }
    }

    override suspend fun saveBitmap(context: Context, bitmap: Bitmap, name: String?) =
        withContext(Dispatchers.IO) {
            val fileName = "${name ?: UUID.randomUUID().toString().replace("-", "")}.png"
            val file = File(context.cacheDir.path /*+ "/image_manager_disk_cache"*/, fileName)
            //file.mkdir()
            //creates outputstream to write the file
            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)//write to outputstream
            }
            file.toUri()
        }
}