package com.example.myapp.ui.viewmodels

import  android.util.Log
import androidx.lifecycle.LiveData
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

abstract class BaseUnsplashModel : ViewModel() {
    abstract val listLiveData: LiveData<List<RowItemType>>

    abstract val errorLiveData: LiveData<String>

    abstract val isLoadingLiveData: LiveData<Boolean>

    abstract val reloadBtnTvEmptyListVisibilityLiveData: LiveData<Boolean>

    abstract val rvMainVisibilityLiveData: LiveData<Boolean>
}

class UnsplashViewModel(
    private val getPhotosUseCase: GetPhotosUseCase
) : BaseUnsplashModel() {

    override val listLiveData = MutableLiveData<List<RowItemType>>()
    override val errorLiveData = MutableLiveData<String>()
    override val isLoadingLiveData = MutableLiveData<Boolean>()
    override val reloadBtnTvEmptyListVisibilityLiveData = MutableLiveData<Boolean>()
    override val rvMainVisibilityLiveData = MutableLiveData<Boolean>()

    private val errorHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.e("UnsplashViewModel", "", throwable)
            throwable.message?.also {
                errorLiveData.value = it
                rvMainVisibilityLiveData.value = false
                isLoadingLiveData.value = false
                reloadBtnTvEmptyListVisibilityLiveData.value = true
            }
        }

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch(errorHandler) {
            reloadBtnTvEmptyListVisibilityLiveData.value = false
            isLoadingLiveData.value = true
            val list = getPhotosUseCase.execute()
            if (list.isNullOrEmpty()) {
                errorLiveData.value = "List is empty"
            } else {
                val listImages = ArrayList(list).apply {
                    add(0, TextItem("Hi"))
                    add(TextItem("Bye"))
                }
                listLiveData.value = listImages
                rvMainVisibilityLiveData.value = true
            }
            isLoadingLiveData.value = false
        }
    }

    //fun from MainActivity
    fun updateValue(model: UnsplashModel) {
        viewModelScope.launch {

            val list = listLiveData.value ?: return@launch
            val rowItemList: List<RowItemType> = list.map {
                when (it) {
                    is UnsplashModel -> if (it.id == model.id) {
                        changeModel(it)
                    } else {
                        it
                    }
                    else -> it
                }
            }
            listLiveData.value = rowItemList
        }
    }

    private fun changeModel(model: UnsplashModel): UnsplashModel {
        return if (model.isLiked) {
            model.copy(likesNumber = model.likesNumber - 1, isLiked = false)
        } else {
            model.copy(likesNumber = model.likesNumber + 1, isLiked = true)
        }
    }

    fun deleteItem(modelId: String) {
        viewModelScope.launch {
            val list = listLiveData.value ?: return@launch
            val item = findItemFromList(list, modelId)
            val rowItemList: List<RowItemType> = ArrayList(list).apply { remove(item) }
            listLiveData.value = rowItemList
        }
    }

    private fun findItemFromList(
        rowItemList: List<RowItemType>,
        modelId: String
    ): UnsplashModel? {

        val unsplashModelList = rowItemList.filterIsInstance<UnsplashModel>()
        return unsplashModelList.find { it.id == modelId }
    }
}












