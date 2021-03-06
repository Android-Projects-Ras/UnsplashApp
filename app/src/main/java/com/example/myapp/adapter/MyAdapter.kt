package com.example.myapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapp.databinding.RowItemImageBinding
import com.example.myapp.databinding.RowItemTextBinding
import com.example.myapp.models.UnsplashModel

const val VIEW_TYPE_IMAGE = 1
const val VIEW_TYPE_TEXT = 2
const val PAYLOAD_IMAGE_ITEM_LIKED = "PayloadImageItemLiked"

class MyAdapter(
    private val likeListener: ((UnsplashModel) -> Unit),
    private val itemClickListener: (
        model: UnsplashModel,
        itemImageView: ImageView
    ) -> Unit
) :
    ListAdapter<RowItemType, RecyclerView.ViewHolder>(UnsplashDiffCallback) {

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

    //Come in changed model
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val currentItem = getItem(position)
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.forEach { payload ->
                (payload as? List<*>)?.forEach { key ->
                    when (key) {
                        PAYLOAD_IMAGE_ITEM_LIKED -> if (currentItem is UnsplashModel) {
                            (holder as UnsplashImageViewHolder).updateLike(currentItem)
                            holder.changeState(currentItem)
                        } else return
                    }
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

        @SuppressLint("ClickableViewAccessibility")
        fun bind(model: UnsplashModel) {

            Glide.with(binding.root)
                .load(model.url)
                //.placeholder(R.drawable.glide_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.ivMainItem)

            binding.viewLikeButton.setLikes(model.likesNumber)
            binding.viewLikeButton.setLiked(model.isLiked)

            binding.viewLikeButton.setOnClickListener {
                likeListener(model)
            }

            //Setting shared element transition
            binding.ivMainItem.transitionName = model.id

            binding.ivMainItem.setOnClickListener {
                itemClickListener(model, binding.ivMainItem)
            }

            /*binding.ivMainItem.setOnTouchListener { v, event ->
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        ValueAnimator.ofFloat(1.0f, 0.8f).apply {
                            addUpdateListener {
                                val scale: Float = it.animatedValue.toString().toFloat()
                                v.scaleX = scale
                                v.scaleY = scale
                            }
                            start()
                        }
                        *//*ObjectAnimator().apply {
                            target = v
                            duration = 500
                            setPropertyName(View.SCALE_X.name)
                            setPropertyName(View.SCALE_Y.name)
                            setFloatValues(1.0f, 0.8f)
                            start()
                        }*//*
                    }
                    MotionEvent.ACTION_UP -> {
                        ValueAnimator.ofFloat(0.8f, 1.0f).apply {
                            addUpdateListener {
                                val scale: Float = it.animatedValue.toString().toFloat()
                                v.scaleX = scale
                                v.scaleY = scale
                            }
                            start()
                        }
                    }

                    MotionEvent.ACTION_CANCEL -> {
                        ValueAnimator.ofFloat(0.8f, 1.0f).apply {
                            addUpdateListener {
                                val scale: Float = it.animatedValue.toString().toFloat()
                                v.scaleX = scale
                                v.scaleY = scale
                            }
                            start()
                        }
                    }
                }
                true
            }*/
        }

        //changed model
        fun updateLike(model: UnsplashModel) {
            binding.viewLikeButton.animateLike(model.likesNumber, model.isLiked)
        }

        fun changeState(model: UnsplashModel) {
                binding.ivMainItem.setOnClickListener {
                    itemClickListener(model, binding.ivMainItem)
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
}