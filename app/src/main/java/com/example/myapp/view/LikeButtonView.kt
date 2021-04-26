package com.example.myapp.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnStart
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

    fun animateLike(isLiked: Boolean) {

        Toast.makeText(context, "animateLike", Toast.LENGTH_SHORT).show()
        val animX = ObjectAnimator().apply {
            target = binding.ivHeart
            duration = 500
            setPropertyName("scaleX")
            setFloatValues(0.8f, 1.1f, 0.9f, 1.0f)
        }

        val animY = ObjectAnimator().apply {
            target = binding.ivHeart
            duration = 500
            setPropertyName("scaleY")
            setFloatValues(0.8f, 1.1f, 0.9f, 1.0f)
        }

        AnimatorSet().apply {
            play(animX).with(animY)
            //start()
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    binding.ivHeart.setImageResource(
                        when (isLiked) {
                            true -> R.drawable.ic_heart_red
                            else -> R.drawable.ic_heart_white
                        }
                    )
                }

                override fun onAnimationEnd(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })
            start()

        }


    }
}

