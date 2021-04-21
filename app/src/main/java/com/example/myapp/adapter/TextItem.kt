package com.example.myapp.adapter

data class TextItem(
    override val title: String,
    override val id: String = "",
    override val url: String = "",
    override val likesNumber: Int = 0,
    override val isLiked: Boolean = false
) : RowItemType