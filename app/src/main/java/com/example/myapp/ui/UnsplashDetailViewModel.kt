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
    private val model: UnsplashModel //todo: убери private val, как поле класса она тут не нужна, есть же unsplashModelLiveData
) : BaseUnsplashDetailViewModel() {

    override val unsplashModelLiveData = MutableLiveData(model)

    //todo: как поле она тут не особо нужна, лучшее просто в конструкторе создать. По сути она дублируется у тебя тут и в лайвдате
    private val descriptionTextModel = DescriptionTextModel(
        description = model.altDescription,
        width = model.width,
        height = model.height,
        likesCount = model.likesNumber,
        isLiked = model.isLiked
    )

    override val descriptionTextLiveData = MutableLiveData(descriptionTextModel)
    override val transitionLiveData = MutableLiveData(model.id)

}