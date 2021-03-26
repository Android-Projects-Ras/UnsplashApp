package com.example.myapp.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.adapter.RowItemType
import com.example.myapp.data.cache.InternalCache
import com.example.myapp.models.UnsplashModel
import com.example.myapp.repository.UnsplashRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UnsplashViewModel(
    private val repository: UnsplashRepository,
    private val internalCache: InternalCache,
    private val context: Context
//        private val unsplashModelDao: UnsplashModelDao

) : ViewModel() {

    val responseLiveData = MutableLiveData<List<RowItemType>>()
    val errorLiveData = MutableLiveData<String>()

    private val errorHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { context, throwable ->
            Log.e("UnsplashViewModel", "" ,throwable  )
            throwable.message?.also {
                errorLiveData.value = it
            }
        }

    init {
        getUnsplashImages()
    }


    fun getUnsplashImages() {
        viewModelScope.launch(errorHandler) {
            val resp = repository.getUnsplashImage()
            responseLiveData.value = resp
            repository.insertAllImages(resp)

            withContext(Dispatchers.IO) {
                cachingImages(resp)
            }
        }
    }

    private suspend fun cachingImages(modelsList: List<UnsplashModel>) {
        modelsList.forEach { _ ->
            val imageBitmap = internalCache.loadBitmap(modelsList[0].urls.regular)
            imageBitmap?.let { internalCache.saveBitmap(context, it, "hi") }
        }
    }


}