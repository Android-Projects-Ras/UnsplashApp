package com.example.myapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.models.UnsplashModel

class UnsplashDetailViewModel(
    private val model: UnsplashModel
): ViewModel() {

    val unsplashModelLiveData = MutableLiveData(model)

}