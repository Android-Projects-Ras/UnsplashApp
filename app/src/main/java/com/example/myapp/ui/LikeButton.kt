package com.example.myapp.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapp.R
import com.example.myapp.databinding.LikeButtonLayoutBinding
import java.util.ArrayList

//todo: вынеси в отдельный пакет view с наименованием LikeButtonView
class LikeButton(context: Context, attrs: AttributeSet?): ConstraintLayout(context, attrs) {

    private var _binding: LikeButtonLayoutBinding? = null
    private val binding get() = _binding!!

    var likesTv: TextView //  todo: не далай поля на вьюхи паблик. Создай паблик методы, которые будут взаимодействовать с этими вью
    var buttonNameTv: TextView



    init {
        val view = inflate(context, R.layout.like_button_layout, this)
        _binding = LikeButtonLayoutBinding.bind(view)
        likesTv = binding.likesTv
        buttonNameTv = binding.buttonNameTv

    }

    //private val likeTv = binding.likesTv

    /*fun setLikes(likes: Int) {  todo: такой вариант норм
        binding.likesTv.text = likes.toString()
    }*/


}