package com.example.myapp.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import com.example.myapp.R
import com.example.myapp.databinding.ViewLikeButtonBinding

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

    fun animateLike(likesAmount: Int, isLiked: Boolean) {
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

        val animSet = AnimatorSet()
        animSet.addListener(
            onStart = {
                setLiked(isLiked)
                binding.tvLikes.isVisible = true
                binding.tvLikes.text = likesAmount.toString()
            }
        )

        when (likesAmount) {
            0 -> animSet.playTogether(animX, animY, alphaFadeOut)
            1 -> {
                val likes: Int? = binding.tvLikes.text.toString().toIntOrNull()
                if (likes != null && likes == 2) {
                    binding.tvLikes.isVisible = true
                    binding.tvLikes.text = likesAmount.toString()
                } else {
                    animSet.playTogether(animX, animY, alphaFadeIn)
                }
            }
            else -> {
                animSet.playTogether(animX, animY)
                binding.tvLikes.isVisible = true
                binding.tvLikes.text = likesAmount.toString()
            }
        }
        animSet.start()
    }
}


