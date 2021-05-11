package com.example.myapp.models

import android.os.Parcelable
import com.example.myapp.adapter.RowItemType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashModel(
    val id: String,
    val description: String,
    val url: String,
    val likesNumber: Int,
    val isLiked: Boolean,
    val altDescription: String,
    val height: Int,
    val width: Int,
    val createdAt: String
) : RowItemType, Parcelable
