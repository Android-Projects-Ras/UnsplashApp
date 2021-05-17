package com.example.myapp.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.scale
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myapp.R
import com.example.myapp.databinding.FragmentDetailImageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KClass

class DetailImageFragment :
    BaseFragment<FragmentDetailImageBinding, UnsplashDetailViewModel>(
        R.layout.fragment_detail_image,
        FragmentDetailImageBinding::inflate
    ) {
    override val viewModelClass: KClass<UnsplashDetailViewModel>
        get() = UnsplashDetailViewModel::class
    //private val args by navArgs<DetailImageFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        viewModel.unsplashModelLiveData.observe(viewLifecycleOwner, { model ->
            val photoUrl = model.url

            Glide.with(binding.root)
                .load(photoUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }
                })
                .into(binding.ivDetail)

            binding.ivDetail.transitionName = model.id

            val date = model.createdAt
            val description = model.altDescription
            val width = model.width.toString()
            val height = model.height.toString()
            val likesCount = model.likesNumber
            val isLiked = model.isLiked

            //val regex = "(.+)(.{15})"

            val necessaryDatePart = date.substring(0, 10)
            val parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).run {
                parse(
                    necessaryDatePart
                )
            }
            val myDate = SimpleDateFormat("dd.MM", Locale.ENGLISH).format(parsedDate).toFloat()

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

            val dateWithRes = getString(R.string.date_detail_fragment, myDate)
            binding.tvDate.text = Html.fromHtml(dateWithRes, FROM_HTML_MODE_LEGACY)
        })

    }

}