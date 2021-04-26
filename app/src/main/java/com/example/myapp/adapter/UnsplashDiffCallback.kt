package com.example.myapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapp.models.UnsplashModel
import java.util.ArrayList

class UnsplashDiffCallback(
    private val oldList: List<RowItemType>,
    private val newList: List<RowItemType>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when (oldItem) {
            is UnsplashModel -> oldItem.id == (newItem as UnsplashModel).id
            else -> true
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when (oldItem) {
            is UnsplashModel -> oldItem.likesNumber == (newItem as UnsplashModel).likesNumber
            else -> true
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition] as UnsplashModel
        val newItem = newList[newItemPosition] as UnsplashModel
        val isLikesNumEquals = oldItem.likesNumber == newItem.likesNumber
        if (isLikesNumEquals.not()) {
            return UPDATE_LIKE
        }
        return null
    }
}

