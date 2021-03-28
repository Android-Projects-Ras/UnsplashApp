package com.example.myapp.data.cache

import androidx.room.*
import com.example.myapp.models.UnsplashModel

@Dao
interface UnsplashModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModels(listModels: List<UnsplashModel>)

    @Query("UPDATE unsplash_model_table SET cachedImagePath=:fileURI WHERE id=:modelId")
    suspend fun updateModel(fileURI: String?, modelId: String)

    @Query("SELECT * FROM unsplash_model_table")
    suspend fun getAllModels(): List<UnsplashModel>

    @Delete
    suspend fun deleteAllModels(listModels: List<UnsplashModel>)

}
