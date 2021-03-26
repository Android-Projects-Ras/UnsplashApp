package com.example.myapp.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.myapp.adapter.RowItemType

//@Entity(tableName = "unsplash_model_table")
data class UnsplashModel(
    val alt_description: String,
    val blur_hash: String,
//    @Ignore
    val categories: List<Any>,
    val color: String,
    val created_at: String,
//    @Ignore
    val current_user_collections: List<Any>,
    //@Embedded(prefix = "desc_")
    val description: Any,
    val height: Int,
    //@PrimaryKey(autoGenerate = false)
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
//    @Ignore
    val links: Links,
//    @Ignore
    val promoted_at: Any,
    /*@Ignore
    val sponsorship: Sponsorship,*/
    val updated_at: String,
    //@Embedded(prefix = "urls_")
    val urls: Urls,
 //   @Ignore
    val user: User,
    val width: Int,
    val cachedImagePath: String?
): RowItemType