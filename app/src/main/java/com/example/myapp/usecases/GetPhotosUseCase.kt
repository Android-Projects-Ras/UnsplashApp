package com.example.myapp.usecases

import android.content.Context
import com.example.myapp.adapter.RowItemType
import com.example.myapp.data.cache.InternalCache
import com.example.myapp.mappers.toUnsplashModel
import com.example.myapp.mappers.toUnsplashModelEntity
import com.example.myapp.models.UnsplashModel
import com.example.myapp.repository.UnsplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

interface GetPhotosUseCase {
    suspend fun execute(): List<RowItemType>
}

class GetPhotosUseCaseImpl(
    private val repository: UnsplashRepository,
    private val internalCache: InternalCache,
    private val context: Context
) : GetPhotosUseCase {

    override suspend fun execute(): List<RowItemType> {
        // get list of images
        val imagesList = getUnsplashImages()
        return imagesList
    }

    private suspend fun getUnsplashImages(): List<RowItemType> {
        try {

            val resp = repository.getUnsplashImage()
            val resultList = ArrayList<RowItemType>(resp)

            withContext(Dispatchers.IO) {
                repository.clearCacheAndRoom()
                val listEntityWithURIs = cachingImages(resp)
                addAllModelsToDb(listEntityWithURIs)
            }
            return resultList

        } catch (e: Exception) {
            e.printStackTrace()
            //errorLiveData.value = e.message
            return getCachedUnsplashImages()
        }
    }

    suspend fun getCachedUnsplashImages(): List<RowItemType> {
        val cachedUnsplashImagesURIs = repository.getAllModels()
        return ArrayList<RowItemType>(cachedUnsplashImagesURIs.map {
            it.toUnsplashModel()
        })
    }

    private suspend fun addAllModelsToDb(resp: List<UnsplashModel>) {
        repository.insertAllImages(resp.map {
            it.toUnsplashModelEntity()
        })
    }

    private suspend fun cachingImages(modelsList: List<UnsplashModel>): List<UnsplashModel> {
        val entityListWithURIs = ArrayList<UnsplashModel>()
        modelsList.forEach { unsplashModel ->
            val imageBitmap = unsplashModel.url.let { internalCache.loadBitmap(it) }
            val fileURI = imageBitmap?.let { internalCache.saveBitmap(context, it) }
            val newUnsplash = unsplashModel.copy(url = fileURI.toString())
            entityListWithURIs.add(newUnsplash)
        }
        return entityListWithURIs
    }
}