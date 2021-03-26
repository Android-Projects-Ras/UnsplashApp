package com.example.myapp.models

import androidx.room.Embedded
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

data class Sponsor(
    val accepted_tos: Boolean,
    val bio: String,
    val first_name: String,
    val for_hire: Boolean,
    @SerializedName("id")
    val sponsorId: String,
    val instagram_username: String,
    @Embedded
    val last_name: Any,
    @Embedded
    val links: LinksX,
    @Embedded
    val location: Any,
    val name: String,
    val portfolio_url: String,
    @Embedded
    val profile_image: ProfileImage,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val twitter_username: String,
    @SerializedName("updated_at")
    val sponsorUpdated_at: String,
    val username: String
)