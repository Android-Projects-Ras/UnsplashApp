package com.example.myapp.models

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class UnsplashModelResponse(
    @SerializedName("alt_description")
    val altDescription: String?,
    @SerializedName("blur_hash")
    val blurHash: String?,
    val categories: List<Any>?,
    val color: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<Any>?,
    val description: String?,
    val height: Int?,
    val id: String?,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean?,
    val likes: Int?,
    val links: Links?,
    @SerializedName("promoted_at")
    val promotedAt: Any?,
    val sponsorship: Sponsorship?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    val urls: Urls?,
    val user: User?,
    val width: Int?
)

data class Links(
    val download: String,
    val download_location: String,
    val html: String,
    val self: String
)

data class LinksX(
    val followers: String,
    val following: String,
    @SerializedName("html")
    val linksXhtml: String,
    @SerializedName("likes")
    val linksXLikes: String,
    val photos: String,
    val portfolio: String,
    val self: String
)

data class LinksXX(
    val followers: String,
    val following: String,
    val html: String,
    @SerializedName("likes")
    val linksLikes: String,
    val photos: String,
    val portfolio: String,
    val self: String
)

data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
)

data class ProfileImageX(
    val large: String,
    val medium: String,
    val small: String
)

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

data class Sponsorship(
    val impression_urls: List<Any>,
    @Embedded
    val sponsor: Sponsor,
    val tagline: String,
    val tagline_url: String
)

data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
)

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