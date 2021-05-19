package com.example.myapp.repository

import android.content.Context
import com.example.myapp.data.api.UnsplashApi
import com.example.myapp.data.cache.UnsplashModelDao
import com.example.myapp.mappers.toUnsplashModel
import com.example.myapp.models.UnsplashModel
import com.example.myapp.models.UnsplashModelEntity
import java.io.File
import java.util.*
import kotlin.random.Random

interface UnsplashRepository {

    suspend fun getUnsplashImage(): List<UnsplashModel>

    suspend fun insertAllImages(list: List<UnsplashModelEntity>)

    suspend fun getAllModels(): List<UnsplashModelEntity>

    suspend fun clearCacheAndRoom()

    suspend fun getFileURIs(): List<String>
}

class UnsplashRepositoryImpl(
    private val unsplashModelDao: UnsplashModelDao,
    context: Context,
    private val api: UnsplashApi

) : UnsplashRepository {

    private val path: String = context.cacheDir.path

    override suspend fun getUnsplashImage(): List<UnsplashModel> {
        //map to UnsplashModel
        return api.searchImages().map {
            val randomedLikes = Random.nextInt(0, 10)
            var randomedState = Random.nextBoolean()
            if (randomedLikes == 0) {
                randomedState = false
            }
            it.toUnsplashModel().copy(likesNumber = randomedLikes, isLiked = randomedState)
        }
    }

    override suspend fun insertAllImages(list: List<UnsplashModelEntity>) {
        unsplashModelDao.insertModels(list)

    }

    override suspend fun getAllModels(): List<UnsplashModelEntity> {
        return unsplashModelDao.getAllModels()
    }

    override suspend fun clearCacheAndRoom() {
        val folder = File(path) //todo: лучше вынести удаление кешированных картинок в InternalCache, где ты их кешируешь
        val filesInFolder = folder.listFiles()
        filesInFolder?.forEach {
            it.delete()
        }
        val listModels = unsplashModelDao.getAllModels()
        unsplashModelDao.deleteAllModels(listModels)
    }

    override suspend fun getFileURIs(): List<String> {
        val fileURIs = ArrayList<String>()
        val folder = File(path)
        val filesInFolder = folder.listFiles()
        filesInFolder?.forEach {
            val fileUri = it.toURI().toString()
            fileURIs.add(fileUri)
        }
        return fileURIs
    }

}