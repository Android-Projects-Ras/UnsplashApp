package com.example.myapp.data.cache

import android.content.Context
import androidx.room.*
import com.example.myapp.models.UnsplashModel

@Database(entities = [UnsplashModel::class], version = 1)
@TypeConverters(Converters::class)

abstract class UnsplashDatabase: RoomDatabase() {

    abstract fun getUnsplashModelDao(): UnsplashModelDao

}
