package com.example.myapp.models

import com.google.gson.annotations.SerializedName

data class LinksX(
    /*val followers: String,
    val following: String,*/
    @SerializedName("html")
    val linksXhtml: String,
    @SerializedName("likes")
    val linksXLikes: String,
    val photos: String,
    val portfolio: String
    //val self: String
)