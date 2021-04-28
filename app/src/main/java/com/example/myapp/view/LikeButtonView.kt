package com.example.myapp.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import com.example.myapp.R
import com.example.myapp.databinding.ViewLikeButtonBinding
import com.example.myapp.models.UnsplashModel

class LikeButtonView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var _binding: ViewLikeButtonBinding? = null
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

    fun setHeartImage(isLiked: Boolean) {
        binding.ivHeart.setImageResource(
            when (isLiked) {
                true -> R.drawable.ic_heart_red
                else -> R.drawable.ic_heart_white
            }
        )
    }

    fun animateLike(model: UnsplashModel) {
        Toast.makeText(context, "animateLike", Toast.LENGTH_SHORT).show()
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
            playTogether(animX, animY, alphaFadeOut, alphaFadeIn)

            addListener(onStart = {
                binding.ivHeart.setImageResource(
                    when (model.isLiked) {
                        true -> R.drawable.ic_heart_red
                        else -> R.drawable.ic_heart_white
                    }
                )
                if (model.likesNumber == 0) {
                    binding.tvLikes.isVisible = false  //?
                } else {
                    binding.tvLikes.isVisible = true
                    binding.tvLikes.text = model.likesNumber.toString()
                }
            })
            start()
        }
    }
}


