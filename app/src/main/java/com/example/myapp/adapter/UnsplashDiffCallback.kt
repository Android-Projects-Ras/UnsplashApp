package com.example.myapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapp.models.UnsplashModel
import java.util.ArrayList

object UnsplashDiffCallback : DiffUtil.ItemCallback<RowItemType>() {

    override fun areItemsTheSame(oldItem: RowItemType, newItem: RowItemType): Boolean {
        return when (oldItem) {
            is UnsplashModel -> if (newItem is UnsplashModel) {
                oldItem.id == newItem.id
            } else false
            is TextItem -> if (newItem is TextItem) {
                oldItem.title == newItem.title
            } else false
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: RowItemType, newItem: RowItemType): Boolean {
        return when (oldItem) {
            is UnsplashModel -> if (newItem is UnsplashModel) {
                oldItem.likesNumber == newItem.likesNumber
            } else false
            is TextItem -> if (newItem is TextItem) {
                oldItem.title == newItem.title
            } else false
            else -> true
        }
    }

    override fun getChangePayload(oldItem: RowItemType, newItem: RowItemType): Any? {
        val payloads = mutableListOf<String>()
        if (oldItem is UnsplashModel && newItem is UnsplashModel) {
            if (oldItem.likesNumber != newItem.likesNumber)
                payloads.add(PAYLOAD_IMAGE_ITEM_LIKED)
            if (oldItem.isLiked != newItem.isLiked)
                payloads.add(PAYLOAD_IMAGE_ITEM_CHANGED)
        }
        return payloads.ifEmpty { null }
    }
}

