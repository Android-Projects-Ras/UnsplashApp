package com.example.myapp.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.scale
import com.example.myapp.R
import com.example.myapp.databinding.ViewDescriptionTextBinding
import com.example.myapp.models.UnsplashModel

class DescriptionTextView(context: Context, attr: AttributeSet?) : ConstraintLayout(context, attr) {

    private val binding: ViewDescriptionTextBinding

    init {
        val view = inflate(context, R.layout.view_description_text, this)
        binding = ViewDescriptionTextBinding.bind(view)
    }

    fun setDescriptionText(model: UnsplashModel) {

        val description = model.altDescription
        val width = model.width.toString()
        val height = model.height.toString()
        val likesCount = model.likesNumber
        val isLiked = model.isLiked

        binding.tvDescription.text = buildSpannedString {
            bold {
                color(Color.BLACK) { scale(1.1f) { append("Description: ") } }
            }
            color(Color.GRAY) { append(description) }

            bold {
                color(Color.BLACK) { scale(1.1f) { append("\nSize: ") } }
            }
            color(Color.GRAY) { append("$width X $height") }

            bold {
                color(Color.BLACK) { scale(1.1f) { append("\nLikes count: ") } }
            }
            if (isLiked) {
                color(Color.RED) { append(likesCount.toString()) }
            } else {
                color(Color.GRAY) { append(likesCount.toString()) }
            }
        }
    }
}