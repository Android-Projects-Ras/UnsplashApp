package com.example.myapp.data.cache

import androidx.room.*
import com.example.myapp.models.UnsplashModel
import com.example.myapp.models.UnsplashModelEntity

@Dao
interface UnsplashModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModels(listModels: List<UnsplashModelEntity>)

    @Query("SELECT * FROM unsplash_model_table")
    suspend fun getAllModels(): List<UnsplashModelEntity>

    @Delete
    suspend fun deleteAllModels(listModels: List<UnsplashModelEntity>)

}
