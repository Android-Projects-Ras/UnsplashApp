package com.example.myapp.repository

import com.example.myapp.data.api.RetrofitInstance
import com.example.myapp.data.cache.InternalCache
import com.example.myapp.models.UnsplashModel
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

class UnsplashRepositoryImpl (

): UnsplashRepository{

    override suspend fun getUnsplashImage(): List<UnsplashModel> {
        return RetrofitInstance.api.searchImages()
    }


}