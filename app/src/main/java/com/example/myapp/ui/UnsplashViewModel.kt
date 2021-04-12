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

                //взять лайкнутый элемент из list
                //изменить поля элемента isLiked & likesNumber
                //записать обновленный list в listLiveData
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
    fun updateValue(status: Boolean, likedElementId: String) {
        //listLiveData.value =
    }


}