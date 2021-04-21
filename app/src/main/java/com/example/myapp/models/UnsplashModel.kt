package com.example.myapp.models

import com.example.myapp.adapter.RowItemType

data class UnsplashModel(
    override val id: String,
    val description: String,
    override val url: String,
    override val likesNumber: Int,
    override val isLiked: Boolean,
    override val title: String = ""
) : RowItemType
