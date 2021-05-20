package com.example.myapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.models.DescriptionTextModel
import com.example.myapp.models.UnsplashModel

abstract class BaseUnsplashDetailViewModel : ViewModel() {
    abstract val unsplashModelLiveData: LiveData<UnsplashModel>

    abstract val descriptionTextLiveData: LiveData<DescriptionTextModel>

    abstract val transitionLiveData: LiveData<String>
}

class UnsplashDetailViewModel(
    model: UnsplashModel
) : BaseUnsplashDetailViewModel() {

    override val unsplashModelLiveData = MutableLiveData(model)
    override val descriptionTextLiveData = MutableLiveData(DescriptionTextModel(
        description = model.altDescription,
        width = model.width,
        height = model.height,
        likesCount = model.likesNumber,
        isLiked = model.isLiked
    ))
    override val transitionLiveData = MutableLiveData(model.id)
}