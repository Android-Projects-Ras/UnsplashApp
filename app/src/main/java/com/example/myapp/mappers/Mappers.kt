package com.example.myapp.mappers

import com.example.myapp.models.UnsplashModel
import com.example.myapp.models.UnsplashModelEntity
import com.example.myapp.models.UnsplashModelResponse

fun UnsplashModelResponse.toUnsplashModel(): UnsplashModel {  // response to domain
    return UnsplashModel(
        id = this.id ?: throw Exception("id is null"),
        description = this.description ?: "",
        url = this.urls?.regular ?: "",
        likesNumber = 0,
        isLiked = false,
        altDescription = this.altDescription ?: "",
        height = this.height ?: 0,
        width = this.width ?: 0,
        createdAt = this.createdAt ?: ""
    )
}

fun UnsplashModelEntity.toUnsplashModel(): UnsplashModel {   //  entity to domain
    return UnsplashModel(
        id = this.id,
        description = this.description ?: "",
        url = this.url ?: "",
        likesNumber = 0,
        isLiked = false,
        altDescription = "",
        height = 0,
        width = 0,
        createdAt = ""
    )
}

fun UnsplashModel.toUnsplashModelEntity(): UnsplashModelEntity {   //  domain to entity
    return UnsplashModelEntity(
        id = this.id,
        description = this.description,
        url = this.url
    )
}


