package com.example.myapp.models

import com.example.myapp.adapter.RowItemType

data class UnsplashModel(
    var id: String,
    val description: String?,
    val url: String?,
    var cachedImagePath: String?

): RowItemType
