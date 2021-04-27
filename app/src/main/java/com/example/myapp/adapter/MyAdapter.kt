package com.example.myapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapp.databinding.RowItemImageBinding
import com.example.myapp.databinding.RowItemTextBinding
import com.example.myapp.models.UnsplashModel

const val VIEW_TYPE_IMAGE = 1
const val VIEW_TYPE_TEXT = 2

const val UPDATE_LIKE = 3

class MyAdapter(private val likeListener: ((UnsplashModel) -> Unit)) :
    ListAdapter<RowItemType, RecyclerView.ViewHolder>(UnsplashDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_IMAGE -> UnsplashImageViewHolder(
                RowItemImageBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            else -> UnsplashTextHolder(
                RowItemTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val currentItem = getItem(position)
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.forEach {
                when (it as Int) {
                    UPDATE_LIKE -> (holder as UnsplashImageViewHolder).updateLike(currentItem as UnsplashModel)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when (holder) {
            is UnsplashImageViewHolder -> holder.bind(currentItem as UnsplashModel)
            is UnsplashTextHolder -> holder.bind(currentItem as TextItem)
        }
    }

    inner class UnsplashTextHolder(private val binding: RowItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(textItem: TextItem) {
            binding.tvItemText.text = textItem.title   //different text
        }
    }

    inner class UnsplashImageViewHolder(private val binding: RowItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: UnsplashModel) {

            Glide.with(binding.root)
                .load(model.url)
                //.placeholder(R.drawable.glide_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.ivMainItem)

            //binding.viewLikeButton.animateLike(model.isLiked)
            binding.viewLikeButton.isSelected = model.isLiked //todo: лишнее, не работает
            binding.viewLikeButton.setLikes(model.likesNumber)

            binding.viewLikeButton.setOnClickListener {
                likeListener(model)
            }
        }

        //changed model
        fun updateLike(model: UnsplashModel) {
            binding.viewLikeButton.animateLike(model)
            //binding.viewLikeButton.setAnimatedLikes(model.likesNumber)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TextItem -> VIEW_TYPE_TEXT
            is UnsplashModel -> VIEW_TYPE_IMAGE
            else -> VIEW_TYPE_IMAGE
        }
    }

    /*fun updateList(newList: List<RowItemType>) {
        val diffCallback = UnsplashDiffCallback(myList, newList)
        val diffResults = DiffUtil.calculateDiff(diffCallback)
        myList = newList
        diffResults.dispatchUpdatesTo(this)
    }*/
}