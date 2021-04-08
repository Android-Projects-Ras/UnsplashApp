package com.example.myapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.adapter.RowItemType
import com.example.myapp.adapter.TextItem
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

        viewModelScope.launch() { //todo: потерял errorHandler, в случае ошибки приложение крашнется
            val listImages = withContext(Dispatchers.IO) {
                val list = getPhotosUseCase.execute()
                ArrayList<RowItemType>(list).apply {
                    add(0, TextItem("Hello"))
                    add(TextItem("Bye"))
                }
            }

            if (listImages.isNullOrEmpty()) { //todo: как он может быть пустым, если ты руками добавляешь TextItem("Hello") и TextItem("Bye"). Проверку нужно делать на данные с бекенда
                errorLiveData.value = "List is empty"
            } else {
                listLiveData.value = listImages
            }
        }
    }
}