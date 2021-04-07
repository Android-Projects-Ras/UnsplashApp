package com.example.myapp.mappers

import com.example.myapp.models.UnsplashModel
import com.example.myapp.models.UnsplashModelEntity
import com.example.myapp.models.UnsplashModelResponse

fun UnsplashModelResponse.toUnsplashModel(): UnsplashModel {  // response to domain
    return UnsplashModel(
        id = this.id ?: throw Exception("id is null"),
        description = this.description ?: "",
        url = this.urls?.regular ?: "",
        likesNumber = 0
    )
}

fun UnsplashModelEntity.toUnsplashModel(): UnsplashModel {   //  entity to domain
    return UnsplashModel(
        id = this.id,
        description = this.description ?: "",
        url = this.url ?: "",
        likesNumber = 0
    )
}

fun UnsplashModel.toUnsplashModelEntity(): UnsplashModelEntity {   //  domain to entity
    return UnsplashModelEntity(
        id = this.id,
        description = this.description,
        url = this.url,
        cachedImagePath = null
    )
}


