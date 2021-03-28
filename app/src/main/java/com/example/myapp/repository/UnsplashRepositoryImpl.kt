package com.example.myapp.repository

import android.content.Context
import com.example.myapp.data.api.RetrofitInstance
import com.example.myapp.data.cache.UnsplashModelDao
import com.example.myapp.models.UnsplashModel
import java.io.File
import java.util.*


class UnsplashRepositoryImpl(
    private val unsplashModelDao: UnsplashModelDao,
    context: Context

) : UnsplashRepository {

    private val path: String = context.cacheDir.path + "/image_manager_disk_cache"

    override suspend fun getUnsplashImage(): List<UnsplashModel> {
        return RetrofitInstance.api.searchImages()
    }

    override suspend fun insertAllImages(list: List<UnsplashModel>) {
        unsplashModelDao.insertModels(list)

    }

    override suspend fun getAllModels(): List<UnsplashModel> {
        return unsplashModelDao.getAllModels()
    }

    override suspend fun clearCacheAndRoom() {
        val folder = File(path)
        val filesInFolder = folder.listFiles()
        filesInFolder?.forEach {
            it.delete()
        }
        val listModels = unsplashModelDao.getAllModels()
        unsplashModelDao.deleteAllModels(listModels)
    }

    override suspend fun getFileURIs(): List<String> {
        val pattern = "[.][0]$".toRegex()
        val fileURIs = ArrayList<String>()
        val folder = File(path)
        val filesInFolder = folder.listFiles()
        filesInFolder?.forEach {
            val fileUri = it.toURI().toString()
            if (pattern.containsMatchIn(fileUri)) {
                fileURIs.add(fileUri)
            }
        }
        return fileURIs
    }

    override suspend fun writeURIsToDb(modelList: List<UnsplashModel>, listURIs: List<String>) {
        modelList.forEach {
            for (i in 0..19) {
                unsplashModelDao.updateModel(listURIs[i], it.id)
            }

        }
    }
}
