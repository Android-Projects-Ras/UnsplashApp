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

class MyAdapter(private val likeListener: ((UnsplashModel) -> Unit)) :
    ListAdapter<RowItemType, RecyclerView.ViewHolder>(imageDiffCallback) {

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when (holder) {
            is UnsplashImageViewHolder -> holder.bind(currentItem as UnsplashModel)
            is UnsplashTextHolder -> holder.bind(currentItem as TextItem)
        }
    }

    inner class UnsplashTextHolder(private val binding: RowItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(textItem: RowItemType) {
            binding.tvItemText.text = textItem.title   //different text
        }
    }

    inner class UnsplashImageViewHolder(private val binding: RowItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: RowItemType/*UnsplashModel*/) {

            Glide.with(binding.root)
                .load(model.url)
                //.placeholder(R.drawable.glide_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.ivMainItem)

            binding.viewLikeButton.isSelected = model.isLiked
            binding.viewLikeButton.setLikes(model.likesNumber)

            binding.viewLikeButton.setOnClickListener {
                likeListener(model as UnsplashModel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TextItem -> VIEW_TYPE_TEXT
            is UnsplashModel -> VIEW_TYPE_IMAGE
            else -> VIEW_TYPE_IMAGE
        }
    }

    companion object {
        val imageDiffCallback = object : DiffUtil.ItemCallback<RowItemType>() {

            override fun areItemsTheSame(oldItem: RowItemType, newItem: RowItemType): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RowItemType, newItem: RowItemType): Boolean {
                return oldItem.url == newItem.url
                        && oldItem.likesNumber == newItem.likesNumber
            }
        }
    }
}