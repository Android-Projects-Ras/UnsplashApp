package com.example.myapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.adapter.RowItemType
import com.example.myapp.data.cache.InternalCache
import com.example.myapp.models.UnsplashModel
import com.example.myapp.repository.UnsplashRepository
import kotlinx.coroutines.*
import java.util.ArrayList

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

    suspend fun getCachedUnsplashImages() {
        val cachedUnsplashImagesURIs = repository.getAllModels()
        responseLiveData.postValue(cachedUnsplashImagesURIs)
    }


    fun getUnsplashImages() {
        viewModelScope.launch(errorHandler) {
            val resp = repository.getUnsplashImage()
            responseLiveData.value = resp
            repository.clearCacheAndRoom()
            addAllModelsToDb(resp)
            withContext(Dispatchers.IO) {
                cachingImages(resp)
            }
        }
    }

    fun addAllModelsToDb(resp: List<UnsplashModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAllImages(resp)
        }
    }

    private suspend fun cachingImages(modelsList: List<UnsplashModel>) {
        val listURIs = ArrayList<String>()
        modelsList.forEach {
            val imageBitmap = internalCache.loadBitmap(it.urls.regular)
            val fileURI =
                imageBitmap?.let { imageBitmap -> internalCache.saveBitmap(context, imageBitmap) }
            listURIs.add(fileURI.toString())
        }
        repository.writeURIsToDb(modelsList, listURIs)
    }

    @RequiresApi(Build.VERSION_CODES.M)         // ?
    fun hasInternetConnection(context: Context): Boolean {
        val conManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = conManager.activeNetwork
        return networkInfo != null
    }

}