package com.example.myapp.data.cache

import androidx.room.*
import com.example.myapp.models.UnsplashModel
/*

@Dao
interface UnsplashModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModel(listModels: List<UnsplashModel>)

    */
/*@Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCachedImagePath(imagePath: String)*//*


    @Query("SELECT * FROM unsplash_model_table")
    suspend fun getAllModels(): List<UnsplashModel>

    @Delete
    suspend fun deleteAllModels(model: UnsplashModel)//listModels: List<UnsplashModel>

}*/
