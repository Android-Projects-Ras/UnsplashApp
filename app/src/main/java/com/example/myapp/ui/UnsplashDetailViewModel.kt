package com.example.myapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.models.DescriptionTextModel
import com.example.myapp.models.UnsplashModel

abstract class BaseUnsplashDetailViewModel : ViewModel() {
    abstract val unsplashModelLiveData: LiveData<UnsplashModel>
    abstract val descriptionTextLiveData: LiveData<DescriptionTextModel>
}

class UnsplashDetailViewModel(
    private val model: UnsplashModel
) : BaseUnsplashDetailViewModel() {

    override val unsplashModelLiveData = MutableLiveData(model)

    private val descriptionTextModel = DescriptionTextModel(
        description = model.altDescription,
        width = model.width,
        height = model.height,
        likesCount = model.likesNumber,
        isLiked = model.isLiked
    )

    override val descriptionTextLiveData = MutableLiveData(descriptionTextModel)


}