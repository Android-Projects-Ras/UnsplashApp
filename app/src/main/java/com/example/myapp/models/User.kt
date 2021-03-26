package com.example.myapp.models

import androidx.room.Embedded

data class User(
    val accepted_tos: Boolean,
    val bio: String,
    val first_name: String,
    val for_hire: Boolean,
    val id: String,
    val instagram_username: String,
    @Embedded
    val last_name: Any,
    @Embedded
    val links: LinksXX,
    @Embedded
    val location: Any,
    val name: String,
    val portfolio_url: String,
    @Embedded
    val profile_image: ProfileImageX,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val twitter_username: String,
    val updated_at: String,
    val username: String
)