package com.example.myapp.ui

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.view.View
import androidx.annotation.RequiresApi
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myapp.R
import com.example.myapp.databinding.FragmentDetailImageBinding
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

class DetailImageFragment ://args
    BaseFragment<FragmentDetailImageBinding, UnsplashDetailViewModel>(
        R.layout.fragment_detail_image,
        FragmentDetailImageBinding::inflate
    ) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        //BaseFragment, give me viewModel with args
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
                        startPostponedEnterTransition()
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

            val date = model.createdAt
            if(date != "") {
                val necessaryDatePart = date.substring(0, 10)

                val parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).run {
                    parse(
                        necessaryDatePart
                    )
                }
                val myDate = SimpleDateFormat("dd.MM", Locale.ENGLISH).format(parsedDate).toFloat()
                val dateWithRes = getString(R.string.date_detail_fragment, myDate)
                binding.tvDate.text = Html.fromHtml(dateWithRes, FROM_HTML_MODE_LEGACY)
            }
        })

        viewModel.descriptionTextLiveData.observe(viewLifecycleOwner, {
            binding.viewDescription.setDescriptionText(it)
        })

        viewModel.transitionLiveData.observe(viewLifecycleOwner, {
            binding.ivDetail.transitionName = it
        })
    }

    override fun getParameters(): ParametersDefinition = {
        parametersOf(DetailImageFragmentArgs.fromBundle(requireArguments()))
    }
}