package com.example.myapp.models

import com.google.gson.annotations.SerializedName

data class LinksXX(
    /*val followers: String,
    val following: String,*/
    val html: String,
    @SerializedName("likes")
    val linksLikes: String,
    val photos: String,
    val portfolio: String,
    val self: String
)