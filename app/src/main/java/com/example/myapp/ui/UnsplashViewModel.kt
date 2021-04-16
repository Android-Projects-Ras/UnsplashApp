package com.example.myapp.ui

import  android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.adapter.RowItemType
import com.example.myapp.adapter.TextItem
import com.example.myapp.models.UnsplashModel
import com.example.myapp.usecases.GetPhotosUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.*

class UnsplashViewModel(

    private val getPhotosUseCase: GetPhotosUseCase

) : ViewModel() {

    val listLiveData = MutableLiveData<List<RowItemType>>()
    val errorLiveData = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    private val errorHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { context, throwable ->
            Log.e("UnsplashViewModel", "", throwable)
            throwable.message?.also {
                errorLiveData.value = it
            }
        }

    init {

        viewModelScope.launch(errorHandler) {
            isLoading.value = true
            val list = getPhotosUseCase.execute()
            if (list.isNullOrEmpty()) {
                errorLiveData.value = "List is empty"
                isLoading.value = false
            } else {
                val listImages = ArrayList<RowItemType>(list).apply {
                    add(0, TextItem("Hello"))
                    add(TextItem("Bye"))
                }
                listLiveData.value = listImages
                isLoading.value = false
            }
        }
    }

    //fun from MainActivity
    fun updateValue(
        _isSelected: Boolean,
        likedModelId: String
    ) {
        viewModelScope.launch {

            val list = listLiveData.value
            val unsplashModelList = list?.filterIsInstance<UnsplashModel>()
            val rowItemList: List<RowItemType> = unsplashModelList?.map {
                val changedModel = changeModel(it, _isSelected)
                if (changedModel.id == likedModelId) {
                    changedModel
                } else {
                    it
                }
            } ?: emptyList()

            val listImages = ArrayList<RowItemType>(rowItemList).apply {
                add(0, TextItem("Hello"))
                add(TextItem("Bye"))
            }
            listLiveData.value = listImages
        }
    }

    private fun changeModel(model: UnsplashModel, _isSelected: Boolean): UnsplashModel {
        return if (_isSelected) {
            model.copy(likesNumber = model.likesNumber + 1, isLiked = _isSelected)
        } else {
            model.copy(likesNumber = model.likesNumber - 1, isLiked = _isSelected)
        }
    }
}