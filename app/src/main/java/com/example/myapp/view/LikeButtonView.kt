package com.example.myapp.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapp.R
import com.example.myapp.databinding.ViewLikeButtonBinding

class LikeButtonView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var _binding: ViewLikeButtonBinding? = null
    private val binding get() = _binding!!

    init {
        val view = inflate(context, R.layout.view_like_button, this)
        _binding = ViewLikeButtonBinding.bind(view)
    }

    fun setLikes(likes: Int) {
        binding.tvLikes.text = likes.toString()
    }

    //todo: нет проверки, что если лайков 0 будет, то прятать вьюху с количеством лайков, как договаривались

}