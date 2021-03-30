package com.example.myapp.usecases

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.myapp.adapter.RowItemType
import com.example.myapp.adapter.TextItem
import com.example.myapp.data.cache.InternalCache
import com.example.myapp.mappers.toUnsplashModel
import com.example.myapp.mappers.toUnsplashModelEntity
import com.example.myapp.models.UnsplashModel
import com.example.myapp.repository.UnsplashRepository
import kotlinx.coroutines.*
import java.io.File
import java.util.ArrayList

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
        val listImages = getUnsplashImages()
        return listImages

    }

    private suspend fun getUnsplashImages(): List<RowItemType> {
        try {

            val resp = repository.getUnsplashImage()
            val resultList = ArrayList<RowItemType>(resp).apply {
                add(0, TextItem("Hello"))
                add(TextItem("Bye"))
            }
            repository.clearCacheAndRoom()

            //withContext(Dispatchers.IO) {
                val listEntityWithURIs = cachingImages(resp)
                addAllModelsToDb(listEntityWithURIs)
                // save models to DB
            //}
            return resultList

        } catch (e: Exception) {
            e.printStackTrace()
            //errorLiveData.value = e.message
            getCachedUnsplashImages()
        }

        return emptyList()
    }

    suspend fun getCachedUnsplashImages(): List<RowItemType> {
        val path: String = context.cacheDir.path + "/image_manager_disk_cache"
        val folder = File(path)
        val filesInFolder = folder.listFiles()
        if (filesInFolder.isNullOrEmpty()) {
            repository.clearCacheAndRoom()
        }
        val cachedUnsplashImagesURIs = repository.getAllModels()


        val resultList = ArrayList<RowItemType>(cachedUnsplashImagesURIs.map {
            it.toUnsplashModel()
        }).apply {
            add(0, TextItem("Hello"))
            add(TextItem("Bye"))
        }
        return resultList

    }


    private fun addAllModelsToDb(resp: List<UnsplashModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertAllImages(resp.map {
                it.toUnsplashModelEntity()
            })
        }
    }

    private suspend fun cachingImages(modelsList: List<UnsplashModel>): List<UnsplashModel> {
        val entityListWithURIs = ArrayList<UnsplashModel>()
        modelsList.forEach {
            val imageBitmap = it.url?.let { it1 -> internalCache.loadBitmap(it1) }
            val fileURI =
                imageBitmap?.let { it -> internalCache.saveBitmap(context, it) }
            it.cachedImagePath = fileURI.toString()
            entityListWithURIs.add(it)

        }

        return entityListWithURIs
    }

}