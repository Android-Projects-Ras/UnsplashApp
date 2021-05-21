package com.example.myapp.view

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapp.R
import com.example.myapp.databinding.ViewCustomToastBinding

class CustomToast(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var binding: ViewCustomToastBinding

    init {
        val view = inflate(context, R.layout.view_custom_toast, this)
        binding = ViewCustomToastBinding.bind(view)

        attrs?.let {
            val styledAttributes = context.obtainStyledAttributes(it, R.styleable.CustomToast, 0, 0)
            var toastText = styledAttributes.getString(R.styleable.CustomToast_text)

            if (toastText != null) {
                setText(toastText)
            } else {
                toastText = ""
                setText(toastText)
            }

            styledAttributes.recycle()
        }
    }

    fun setText(text: String) {
        binding.tvCustomToastText.text = text
    }
}