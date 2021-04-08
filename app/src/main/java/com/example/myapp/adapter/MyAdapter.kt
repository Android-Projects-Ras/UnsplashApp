package com.example.myapp.adapter

import android.content.res.Resources
import android.graphics.drawable.StateListDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapp.R
import com.example.myapp.databinding.RowItemImageBinding
import com.example.myapp.databinding.RowItemTextBinding
import com.example.myapp.models.UnsplashModel
import com.example.myapp.models.UnsplashModelEntity
import kotlin.random.Random


const val VIEW_TYPE_IMAGE = 1
const val VIEW_TYPE_TEXT = 2

class MyAdapter(/* todo: Сюда колбек в фрагмент для прослушивания нажатия на кнопку */) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


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

        val randomed = Random.nextInt(0, 10)
        var isLiked = true // todo: поле должно быть в модели, чтобы не было того, что я скинул тебе на видео

        fun bind(model: UnsplashModel) {


            Glide.with(binding.root)
                .load(model.url)
                //.placeholder(R.drawable.glide_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.imageView)

            (myList[adapterPosition] as UnsplashModel).likesNumber = randomed //todo:  удали рандом из адаптера, можешь вынести в репозиторий в место, где мапишь
            val randomedLikesNumber = (myList[layoutPosition] as UnsplashModel).likesNumber
            binding.likesTv.text = randomedLikesNumber.toString()

            binding.likeButton.setOnClickListener {
                if (isLiked) {
                    binding.likesTv.text = randomedLikesNumber.plus(1).toString()
                    binding.likeButton.isSelected = true //todo:  обновление лайков через обновление полей модели, которое нужно делать во вью модели
                    isLiked = false

                } else {
                    binding.likesTv.text = randomedLikesNumber.toString()
                    binding.likeButton.isSelected = false
                    isLiked = true
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (myList[position]) {
            is TextItem -> VIEW_TYPE_TEXT
            is UnsplashModel -> VIEW_TYPE_IMAGE
            else -> VIEW_TYPE_IMAGE
        }
    }

    /*interface OnLikeClickListener {
        fun onLikeClick()
    }*/
}