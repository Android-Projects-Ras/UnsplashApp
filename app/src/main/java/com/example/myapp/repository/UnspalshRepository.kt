package com.example.myapp.repository

import com.example.myapp.models.UnsplashModel

interface UnsplashRepository {

    suspend fun getUnsplashImage(): List<UnsplashModel>

    suspend fun insertAllImages(list: List<UnsplashModel>)

    suspend fun getAllModels(): List<UnsplashModel>

    suspend fun clearCacheAndRoom()

    suspend fun getFileURIs(): List<String>

    suspend fun writeURIsToDb(modelList: List<UnsplashModel>, listURIs: List<String>)

}