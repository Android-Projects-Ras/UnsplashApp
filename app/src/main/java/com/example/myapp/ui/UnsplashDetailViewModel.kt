package com.example.myapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.models.UnsplashModel

abstract class BaseUnsplashDetailViewModel: ViewModel() {
    abstract val unsplashModelLiveData: LiveData<UnsplashModel>
}

class UnsplashDetailViewModel(
    private val model: UnsplashModel
): BaseUnsplashDetailViewModel() {

    override val unsplashModelLiveData get() = MutableLiveData(model)

}