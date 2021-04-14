package com.example.myapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.adapter.RowItemType
import com.example.myapp.adapter.TextItem
import com.example.myapp.models.UnsplashModel
import com.example.myapp.usecases.GetPhotosUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList

//@RequiresApi(Build.VERSION_CODES.M)
class UnsplashViewModel(

    private val getPhotosUseCase: GetPhotosUseCase

) : ViewModel() {


    val listLiveData = MutableLiveData<List<RowItemType>>()
    val errorLiveData = MutableLiveData<String>()

    private val errorHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { context, throwable ->
            Log.e("UnsplashViewModel", "", throwable)
            throwable.message?.also {
                errorLiveData.value = it
            }
        }

    init {

        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                val list = getPhotosUseCase.execute()
                if (list.isNullOrEmpty()) {
                    errorLiveData.value = "List is empty"
                } else {
                    val listImages = ArrayList<RowItemType>(list).apply {
                        add(0, TextItem("Hello"))
                        add(TextItem("Bye"))
                    }
                    listLiveData.postValue(listImages)
                }
            }
        }
    }

    //fun from MainActivity
    fun updateValue(status: Boolean, likedModelId: String) {
        viewModelScope.launch {

            val list = listLiveData.value
            val unsplashModelList = toUnsplashModelList(list)
            val rowItemList = changeLikedModel(unsplashModelList, status, likedModelId)

            listLiveData.postValue(rowItemList)
        }
    }

    private fun toUnsplashModelList(list: List<RowItemType>?): List<UnsplashModel> {
        val unsplashList = ArrayList<UnsplashModel>()
        list?.forEach {
            when (it) {
                is UnsplashModel -> unsplashList.add(it)
                is TextItem -> return@forEach
            }
        }
        return unsplashList
    }

    private fun changeLikedModel(
        unsplashModelList: List<UnsplashModel>,
        status: Boolean,
        likedModelId: String
    ): List<RowItemType> {

        val rowItemList = ArrayList<RowItemType>()

        unsplashModelList.forEach { unsplashModel ->
            if (unsplashModel.id == likedModelId) {
                if (status) {
                    val likes = unsplashModel.likesNumber+1
                    unsplashModel.likesNumber = likes   //unsplashModel.likesNumber.plus(1) doesn't work
                    unsplashModel.isLiked = status
                } else {
                    val likes = unsplashModel.likesNumber-1
                    unsplashModel.likesNumber = likes
                    unsplashModel.isLiked = status
                }
                rowItemList.add(unsplashModel)
            } else {
                rowItemList.add(unsplashModel)

            }
        }
        return rowItemList
    }


}