package com.example.myapp.models

import com.example.myapp.adapter.RowItemType

data class UnsplashModel(
    val id: String,
    val description: String,
    val url: String,
    var likesNumber: Int,
    var isLiked: Boolean // todo: не делай поля var. у дата классов есть метод copy, который создаёт копию объекта и там в конструктор ты можешь передать новые значения

): RowItemType
