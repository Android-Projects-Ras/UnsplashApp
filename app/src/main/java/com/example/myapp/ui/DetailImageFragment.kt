package com.example.myapp.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.myapp.R
import com.example.myapp.databinding.FragmentDetailImageBinding

class DetailImageFragment: Fragment(R.layout.fragment_detail_image) {

    private var _binding: FragmentDetailImageBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailImageFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move);
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailImageBinding.bind(view)

        val photoUrl = args.unsplashModel.url

        Glide.with(binding.root)
            .load(photoUrl)
            .into(binding.ivDetail)

        //binding.tvDate.text = args.unsplashModel



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}