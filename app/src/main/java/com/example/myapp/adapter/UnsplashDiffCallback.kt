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
            is UnsplashModel -> oldItem.id == (newItem as UnsplashModel).id //todo: новый айтем может быть вовсе не UnsplashModel, если ты например его во вью модели заменишь на другой тип. И будет краш, нужно делать проверку на тип
            else -> true
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when (oldItem) {
            is UnsplashModel -> oldItem.likesNumber == (newItem as UnsplashModel).likesNumber //todo: новый айтем может быть вовсе не UnsplashModel, если ты например его во вью модели заменишь на другой тип. И будет краш, нужно делать проверку на тип
            else -> true
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition] as UnsplashModel //todo: так делать не стоит, потому что тут же может потом быть не только UnsplashModel и придётся переписывать. Делай проверку типов как в предыдущих методах
        val newItem = newList[newItemPosition] as UnsplashModel
        val isLikesNumEquals = oldItem.likesNumber == newItem.likesNumber
        if (isLikesNumEquals.not()) {
            return UPDATE_LIKE
        }
        return null
    }
}

