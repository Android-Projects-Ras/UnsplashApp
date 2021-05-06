package com.example.myapp.view

import CustomAnimations
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

    private var binding: ViewLikeButtonBinding

    init {
        val view = inflate(context, R.layout.view_like_button, this)
        binding = ViewLikeButtonBinding.bind(view)
    }

    fun setLikes(likes: Int) {
        if (likes == 0) {
            binding.tvLikes.visibility = View.INVISIBLE
        } else {
            binding.tvLikes.visibility = View.VISIBLE
            binding.tvLikes.text = likes.toString()
        }
    }

    fun setLiked(isLiked: Boolean) {
        binding.ivHeart.setImageResource(
            when (isLiked) {
                true -> R.drawable.ic_heart_red
                else -> R.drawable.ic_heart_white
            }
        )
    }

    fun animateLike(model: UnsplashModel) { //todo: cразу не обратил внимания...Не передавай сюда всю модель, тебе тут нужны только количество лайков и лайнул ли ты. Вью должна получать только те данные, которые ей нужны
        /*val animX = ObjectAnimator().apply {
            target = binding.ivHeart
            duration = 500
            setPropertyName(View.SCALE_X.name)
            setFloatValues(0.8f, 1.1f, 0.9f, 1.0f)
        }*/

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

        val animSet = AnimatorSet()
        setLiked(model.isLiked) // todo: перенеси в onStart аниманции
        when (model.likesNumber) {
            0 -> animSet.playTogether(CustomAnimations().animX, animY, alphaFadeOut)
            1 -> {
                val likes: Int? = binding.tvLikes.text.toString().toIntOrNull()
                if (likes != null && likes == 2) {
                    binding.tvLikes.isVisible = true
                    binding.tvLikes.text = model.likesNumber.toString()
                    return // замени на if else конструкцию лучше
                }
                binding.tvLikes.isVisible = true //todo: перенеси в onStart блок для анимации
                animSet.playTogether(CustomAnimations().animX, animY, alphaFadeIn)
                binding.tvLikes.text = model.likesNumber.toString() //todo: перенеси в onStart блок для анимации
            }
            else -> {
                binding.tvLikes.isVisible = true
                binding.tvLikes.text = model.likesNumber.toString()
            }
        }
        animSet.start()
    }
}


