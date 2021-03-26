package com.example.myapp.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.adapter.LIST_SIZE
import com.example.myapp.adapter.RowItemType
import com.example.myapp.data.cache.InternalCache
import com.example.myapp.models.UnsplashModel
import com.example.myapp.repository.UnsplashRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class UnsplashViewModel(
        private val repository: UnsplashRepository
        //private val internalCache: InternalCache
        //private val context: Context
//        private val unsplashModelDao: UnsplashModelDao

) : ViewModel() {//InstanceCreationException: Could not create instance for [Factory:'com.example.myapp.ui.UnsplashViewModel']

    val responseLiveData = MutableLiveData<List<RowItemType>>()
    val errorLiveData = MutableLiveData<String>()

    init {
        getUnsplashImages()
    }

    private val errorHandler: CoroutineExceptionHandler = CoroutineExceptionHandler() { context, throwable ->
        val errorMessage = throwable.message
        errorLiveData.value = errorMessage!!
    }




    fun getUnsplashImages() {
        viewModelScope.launch(errorHandler) {
            val resp = repository.getUnsplashImage()
            responseLiveData.value = resp
            //cachingImages(resp)


        }
    }

    /*private suspend fun cachingImages(modelsList: List<UnsplashModel>) {
        modelsList.forEach { _ ->
            val imageBitmap = internalCache.loadBitmap(modelsList[0].urls.regular)
            val savedImageBitmap = imageBitmap?.let { internalCache.saveBitmap(context, it, null) }
//            unsplashModelDao.insertModel(modelsList)
        }
    }*/


}