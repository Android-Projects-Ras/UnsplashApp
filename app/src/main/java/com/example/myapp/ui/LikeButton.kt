package com.example.myapp.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapp.R


class LikeButton(context: Context, attrs: AttributeSet?): CardView(context, attrs) {

    init {
        inflate(context, R.layout.like_button_layout, this)
    }

}