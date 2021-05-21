package com.example.myapp.ui.viewmodels

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModel
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentListImagesBinding

class MainActivityViewModel : ViewModel() {

    fun translateToast(binding: ActivityMainBinding) {
        val animYDown = ObjectAnimator.ofFloat(binding.viewCustomToast, "y", 250f)
        animYDown.duration = 1000/*.apply {
        animYDown
            target = binding.viewCustomToast
            duration = 500
            setPropertyName(View.TRANSLATION_Y.name)
            setFloatValues(0.0f, 5.0f)
        }*/
        val animYUp = ObjectAnimator.ofFloat(binding.viewCustomToast, "y", -250f)
        animYUp.duration = 1000

        AnimatorSet().apply {
            playSequentially(animYDown, animYUp)
            start()
        }
    }
}