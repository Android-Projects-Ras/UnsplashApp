package com.example.myapp.ui.viewmodels

import android.animation.ObjectAnimator
import android.view.View
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    fun translateToast(view: View) {
        ObjectAnimator().apply {
            target = view
            duration = 2000
            setPropertyName(View.TRANSLATION_Y.name)
            setFloatValues(0f, 230f, 220f, 230f, 0f)
            start()
        }
    }
}