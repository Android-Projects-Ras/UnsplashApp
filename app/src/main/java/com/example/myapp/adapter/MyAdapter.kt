package com.example.myapp.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myapp.data.cache.InternalCache
import com.example.myapp.data.cache.InternalCacheImpl
import com.example.myapp.databinding.RowItemFooterBinding
import com.example.myapp.databinding.RowItemHeaderBinding
import com.example.myapp.databinding.RowItemImageBinding
import com.example.myapp.models.UnsplashModel


const val VIEW_TYPE_HEADER = 0
const val VIEW_TYPE_IMAGE = 1
const val VIEW_TYPE_FOOTER = 2

class MyAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var myList = emptyList<RowItemType>()


    inner class UnsplashHeaderHolder(private val binding: RowItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.textView.text = "Welcome!"
        }

    }

    inner class UnsplashImageViewHolder(val binding: RowItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: UnsplashModel) {

            Glide.with(binding.root)
                .load(model.urls.regular)
                .into(binding.imageView)
        }
    }

    class UnsplashFooterHolder(private val binding: RowItemFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.textView.text = "That is all!"
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return VIEW_TYPE_HEADER
        return if (position == myList.size - 1)
            VIEW_TYPE_FOOTER
        else VIEW_TYPE_IMAGE
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_IMAGE -> UnsplashImageViewHolder(
                RowItemImageBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            VIEW_TYPE_HEADER -> UnsplashHeaderHolder(
                RowItemHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> UnsplashFooterHolder(
                RowItemFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UnsplashImageViewHolder -> holder.bind(myList[position] as UnsplashModel)
            is UnsplashFooterHolder -> holder.bind()
            is UnsplashHeaderHolder -> holder.bind()
        }
    }

    override fun getItemCount() = myList.size

    fun setData(newList: List<RowItemType>) {
        myList = newList
        notifyDataSetChanged()
    }
}