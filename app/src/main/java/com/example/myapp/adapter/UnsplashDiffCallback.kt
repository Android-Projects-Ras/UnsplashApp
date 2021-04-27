package com.example.myapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapp.models.UnsplashModel
import java.util.ArrayList

class UnsplashDiffCallback : DiffUtil.ItemCallback<RowItemType>() {

    override fun areItemsTheSame(oldItem: RowItemType, newItem: RowItemType): Boolean {
        if (oldItem is UnsplashModel && newItem is UnsplashModel) {
            return oldItem.id == newItem.id /*&&
                    oldItem.url == newItem.url*/
        }
        return true
    }

    override fun areContentsTheSame(oldItem: RowItemType, newItem: RowItemType): Boolean {
        if (oldItem is UnsplashModel && newItem is UnsplashModel) {
            return oldItem.likesNumber == newItem.likesNumber
        }
        return true
    }

    override fun getChangePayload(oldItem: RowItemType, newItem: RowItemType): Any? {
        if (oldItem is UnsplashModel && newItem is UnsplashModel) {
            val comparison = oldItem.likesNumber == newItem.likesNumber
            return when (comparison) {
                true -> null
                else -> UPDATE_LIKE
            }
        }
        return null
    }
}

