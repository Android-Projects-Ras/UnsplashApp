package com.example.myapp.models

data class Sponsorship(
    val impression_urls: List<Any>,
    val sponsor: Sponsor,
    val tagline: String,
    val tagline_url: String
)