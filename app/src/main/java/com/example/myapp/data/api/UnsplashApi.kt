package com.example.myapp.data.api

import com.example.myapp.models.UnsplashModelResponse
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.unsplash.com/photos/?client_id=EDNeu164w02HkCLPq1bxesKUU6VrodupWZUHNPnaYMc&per_page=10

interface UnsplashApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val API_KEY = "EDNeu164w02HkCLPq1bxesKUU6VrodupWZUHNPnaYMc"
    }

    @GET("photos")
    suspend fun searchImages(
        @Query("page")
        pageNumber: Int = 1,
        @Query("per_page")
        itemsNumber: Int = 20
    ): List<UnsplashModelResponse>
}
