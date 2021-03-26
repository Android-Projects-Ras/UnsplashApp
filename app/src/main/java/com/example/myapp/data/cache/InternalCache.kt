package com.example.myapp.data.cache

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

interface InternalCache {
    fun loadBitmap(path: String): Bitmap?
    suspend fun saveBitmap(context: Context, bitmap: Bitmap, name: String? = null): Uri

}