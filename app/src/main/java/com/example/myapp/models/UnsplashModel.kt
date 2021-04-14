package com.example.myapp.models

import com.example.myapp.adapter.RowItemType

data class UnsplashModel(
    val id: String,
    val description: String,
    val url: String,
    var likesNumber: Int,
    var isLiked: Boolean

): RowItemType
