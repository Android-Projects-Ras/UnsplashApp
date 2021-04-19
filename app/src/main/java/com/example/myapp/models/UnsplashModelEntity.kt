package com.example.myapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unsplash_model_table")
data class UnsplashModelEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val description: String?,
    val url: String?
)
