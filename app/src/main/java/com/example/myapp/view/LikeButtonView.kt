package com.example.myapp.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.myapp.R
import com.example.myapp.databinding.ViewLikeButtonBinding
import com.example.myapp.models.UnsplashModel

class LikeButtonView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var _binding: ViewLikeButtonBinding? = null //todo: для чего тебе тут 2 поля, ещё и небезопасный доступ к _binding используешь. Если тебе не нужны нуллабл, делай переменные lateinit var
    private val binding get() = _binding!!

    init {
        val view = inflate(context, R.layout.view_like_button, this)
        _binding = ViewLikeButtonBinding.bind(view)
    }

    fun setLikes(likes: Int) {
        if (likes == 0) {
            binding.tvLikes.isVisible = false
        } else {
            binding.tvLikes.isVisible = true
            binding.tvLikes.text = likes.toString()
        }
    }

    //todo: лучше назвать метод setLiked. Ты говоришь вьюхе лайкнута она или нет и в зависимости от этого выбираешь изображение
    fun setHeartImage(isLiked: Boolean) {
        binding.ivHeart.setImageResource(
            when (isLiked) {
                true -> R.drawable.ic_heart_red
                else -> R.drawable.ic_heart_white
            }
        )
    }

    fun animateLike(model: UnsplashModel) {
        val animX = ObjectAnimator().apply {
            target = binding.ivHeart
            duration = 500
            setPropertyName(View.SCALE_X.name)
            setFloatValues(0.8f, 1.1f, 0.9f, 1.0f)
        }

        val animY = ObjectAnimator().apply {
            target = binding.ivHeart
            duration = 500
            setPropertyName(View.SCALE_Y.name)
            setFloatValues(0.8f, 1.1f, 0.9f, 1.0f)
        }

        val alphaFadeIn = ObjectAnimator().apply {
            target = binding.tvLikes
            duration = 500
            setPropertyName(View.ALPHA.name)
            setFloatValues(0f, 1.0f)
        }

        val alphaFadeOut = ObjectAnimator().apply {
            target = binding.tvLikes
            duration = 500
            setPropertyName(View.ALPHA.name)
            setFloatValues(1.0f, 0f)
        }

        AnimatorSet().apply {
            //todo: зачем ты всё занёс в этот блок, если по сути используешь только метод playTogether и start. Отрефактори это, разбей и вынеси лишнее отсюда
            binding.ivHeart.setImageResource(
                when (model.isLiked) {
                    true -> R.drawable.ic_heart_red
                    else -> R.drawable.ic_heart_white
                }
            )
            if (model.likesNumber == 0) {
                playTogether(animX, animY, alphaFadeOut)
            } else {
                binding.tvLikes.isVisible = true
                playTogether(animX, animY, alphaFadeOut, alphaFadeIn)
                binding.tvLikes.text = model.likesNumber.toString()
            }
            start()
        }
    }
}


