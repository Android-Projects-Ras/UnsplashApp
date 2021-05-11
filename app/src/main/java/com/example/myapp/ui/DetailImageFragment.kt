package com.example.myapp.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PatternMatcher
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.scale
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.myapp.R
import com.example.myapp.databinding.FragmentDetailImageBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class DetailImageFragment: Fragment(R.layout.fragment_detail_image) {

    private var _binding: FragmentDetailImageBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailImageFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(
                android.R.transition.slide_left
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailImageBinding.bind(view)

        val photoUrl = args.UnsplashModel.url

        Glide.with(binding.root)
            .load(photoUrl)
            .into(binding.ivDetail)

        val date = args.UnsplashModel.createdAt
        val description = args.UnsplashModel.altDescription
        val width = args.UnsplashModel.width.toString()
        val height = args.UnsplashModel.height.toString()
        val likesCount = args.UnsplashModel.likesNumber
        val isLiked = args.UnsplashModel.isLiked

        //val regex = "(.+)(.{15})"

        val necessaryDatePart = date.substring(0, 10)
        val parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).run { parse(necessaryDatePart) }
        val myDate = SimpleDateFormat("dd.MM", Locale.ENGLISH).format(parsedDate)

        binding.tvDescription.text = buildSpannedString {
            bold {
                color(Color.BLACK) {scale(1.1f) {append("Description: ")}}
            }
            color(Color.GRAY) {append(description)}

            bold {
                color(Color.BLACK) {scale(1.1f) {append("\nSize: ")}}
            }
            color(Color.GRAY) {append("$width X $height")}

            bold {
                color(Color.BLACK) {scale(1.1f) {append("\nLikes count: ")}}
            }
            if (isLiked) {
                color(Color.RED) {append(likesCount.toString())}
            } else {
                color(Color.GRAY) {append(likesCount.toString())}
            }
        }

        binding.tvDate.text = buildSpannedString {
            bold {
                color(Color.BLACK) { append("Date: ") }
            }
            color(Color.GRAY) {append(myDate)}
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}