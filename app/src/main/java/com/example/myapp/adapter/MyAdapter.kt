package com.example.myapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapp.R
import com.example.myapp.databinding.RowItemImageBinding
import com.example.myapp.databinding.RowItemTextBinding
import com.example.myapp.models.UnsplashModel
import com.example.myapp.models.UnsplashModelEntity


const val VIEW_TYPE_IMAGE = 1
const val VIEW_TYPE_TEXT = 2

class MyAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var myList = emptyList<RowItemType>()

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
        when (holder) {
            is UnsplashImageViewHolder -> holder.bind(myList[position] as UnsplashModel)
            is UnsplashTextHolder -> holder.bind(myList[position] as TextItem)
        }
    }

    override fun getItemCount() = myList.size

    fun setData(newList: List<RowItemType>) {
        myList = newList
        notifyDataSetChanged()
    }

    inner class UnsplashTextHolder(private val binding: RowItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(textItem: TextItem) {
            binding.textView.text = textItem.title   //different text
        }

    }

    inner class UnsplashImageViewHolder(private val binding: RowItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: UnsplashModel) {

            Glide.with(binding.root)
                .load(model.url)
                .placeholder(R.drawable.spinner)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.imageView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(myList[position]) {
            is TextItem -> VIEW_TYPE_TEXT
            is UnsplashModel -> VIEW_TYPE_IMAGE
            else -> VIEW_TYPE_IMAGE
        }
    }
}