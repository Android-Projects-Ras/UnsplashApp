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

        viewModelScope.launch() {
            val listImages = withContext(Dispatchers.IO) {
                val list = getPhotosUseCase.execute()
                ArrayList<RowItemType>(list).apply {
                    add(0, TextItem("Hello"))
                    add(TextItem("Bye"))
                }
            }

            if (listImages.isNullOrEmpty()) {
                errorLiveData.value = "List is empty"
            } else {
                listLiveData.value = listImages
            }
        }

    }


    /*@RequiresApi(Build.VERSION_CODES.M)         // ?
    private fun hasInternetConnection(context: Context): Boolean {
        val conManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = conManager.activeNetwork
        return networkInfo != null
    }*/

}