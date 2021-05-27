package com.example.myapp.ui.viewmodels

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.components.SoundVibroService
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    val toastTextLiveData = MutableLiveData<String>()
    val binderLiveData = MutableLiveData<SoundVibroService.SoundVibroServiceBinder?>()

    /*var bound = false
    val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as SoundVibroService.SoundVibroServiceBinder
            binderLiveData.value = binder
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binderLiveData.value = null
            bound = false
        }
    }*/

    fun setToastText(text: String) {
        toastTextLiveData.value = text
    }

    fun getToastText(): String? {
        return toastTextLiveData.value
    }


}