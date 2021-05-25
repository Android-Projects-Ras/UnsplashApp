package com.example.myapp.ui.viewmodels

import android.animation.ObjectAnimator
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    val toastTextLiveData = MutableLiveData<String>()

    fun setToastText(text: String) {
        toastTextLiveData.value = text
    }



}