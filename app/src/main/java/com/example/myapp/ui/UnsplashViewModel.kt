package com.example.myapp.ui

import android.accounts.NetworkErrorException
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.adapter.RowItemType
import com.example.myapp.adapter.TextItem
import com.example.myapp.data.cache.InternalCache
import com.example.myapp.mappers.toUnsplashModel
import com.example.myapp.mappers.toUnsplashModelEntity
import com.example.myapp.models.UnsplashModel
import com.example.myapp.models.UnsplashModelEntity
import com.example.myapp.repository.UnsplashRepository
import kotlinx.coroutines.*
import java.io.IOException
import java.util.ArrayList

@RequiresApi(Build.VERSION_CODES.M)
class UnsplashViewModel(
    private val repository: UnsplashRepository,
    private val internalCache: InternalCache,
    private val context: Context

) : ViewModel() {


    val responseLiveData = MutableLiveData<List<RowItemType>>()
    val errorLiveData = MutableLiveData<String>()

    private val errorHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { context, throwable ->
            Log.e("UnsplashViewModel", "", throwable)
            throwable.message?.also {
                errorLiveData.value = it
            }
        }

    init {

        try{
            getUnsplashImages()
        } catch (e: IOException) {

            viewModelScope.launch(Dispatchers.IO) {
                errorLiveData.postValue("List is empty")
                getCachedUnsplashImages()
            }
        }
        /*if (hasInternetConnection(context)) {
            getUnsplashImages()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                getCachedUnsplashImages()
            }
        }*/
    }

    private suspend fun getCachedUnsplashImages() {
        val cachedUnsplashImagesURIs = repository.getAllModels()
        responseLiveData.postValue(cachedUnsplashImagesURIs.map {
            it.toUnsplashModel()
        })
    }


    private fun getUnsplashImages() {
        viewModelScope.launch(errorHandler) {
            val resp = repository.getUnsplashImage()
            val resultList = ArrayList<RowItemType>(resp).apply {
                add(0, TextItem("Hello"))
                add(TextItem("Bye"))
            }
            responseLiveData.value = resultList
            repository.clearCacheAndRoom()
            //addAllModelsToDb(resp)
            withContext(Dispatchers.IO) {
                val listEntityWithURIs = cachingImages(resp)
                addAllModelsToDb(listEntityWithURIs)             // save models to DB

            }
        }
    }

    private fun addAllModelsToDb(resp: List<UnsplashModel>) {
        viewModelScope.launch(Dispatchers.IO) {
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
                imageBitmap?.let { imageBitmap -> internalCache.saveBitmap(context, imageBitmap) }
            it.cachedImagePath = fileURI.toString()
            entityListWithURIs.add(it)

        }

        return entityListWithURIs
    }

    @RequiresApi(Build.VERSION_CODES.M)         // ?
    private fun hasInternetConnection(context: Context): Boolean {
        val conManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = conManager.activeNetwork
        return networkInfo != null
    }

}