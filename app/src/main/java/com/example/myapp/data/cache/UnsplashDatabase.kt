package com.example.myapp.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapp.models.UnsplashModelEntity

@Database(entities = [UnsplashModelEntity::class], version = 1)
@TypeConverters(Converters::class)

abstract class UnsplashDatabase: RoomDatabase() {

    abstract fun getUnsplashModelDao(): UnsplashModelDao

}
