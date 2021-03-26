package com.example.myapp.models

import androidx.room.Embedded

data class Sponsorship(
    val impression_urls: List<Any>,
    @Embedded
    val sponsor: Sponsor,
    val tagline: String,
    val tagline_url: String
)