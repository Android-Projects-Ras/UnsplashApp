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

    fun animateLike(model: UnsplashModel/*animate: Boolean*/) {
        Toast.makeText(context, "animateLike", Toast.LENGTH_SHORT).show()
        val animX = ObjectAnimator().apply {
            target = binding.ivHeart
            duration = 500
            setPropertyName(View.SCALE_X.name)
//            setPropertyName("ScaleX") // todo: doesn't work View.SCALE_X.toString()
            setFloatValues(0.8f, 1.1f, 0.9f, 1.0f)
        }

        val animY = ObjectAnimator().apply {
            target = binding.ivHeart
            duration = 500
            setPropertyName(View.SCALE_Y.name) // todo: works good)
//            setPropertyName("ScaleY") //View.SCALE_Y.toString()
            setFloatValues(0.8f, 1.1f, 0.9f, 1.0f)
        }

        AnimatorSet().apply {
            play(animX).with(animY) // can be replaced with playTogether()

            addListener(onStart = {
                binding.ivHeart.setImageResource(
                    when (model.isLiked) {
                        true -> R.drawable.ic_heart_red
                        else -> R.drawable.ic_heart_white
                    }
                )
            })
            start()
        }

        setAnimatedLikes(model.likesNumber)
    }

    //todo: object animator и в onStart можешь задавать видимость вью
    private fun setAnimatedLikes(likesAmount: Int) {
        if (likesAmount == 0) {
            val fadeOutAnimation = AlphaAnimation(1.0f, 0f)
            fadeOutAnimation.duration = 500
            binding.tvLikes.startAnimation(fadeOutAnimation)
        } else {
            //todo: tvLikes doesn't appear
            binding.tvLikes.text = likesAmount.toString()
            val fadeInAnimation = AlphaAnimation(0.5f, 1.0f)
            fadeInAnimation.duration = 500
            binding.tvLikes.startAnimation(fadeInAnimation)
        }
    }
}


