package com.example.myapp.adapter

interface RowItemType {
    val title: String
    val id: String
    val url: String
    val likesNumber: Int
    val isLiked: Boolean
}