package com.example.myapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.myapp.R
import com.example.myapp.adapter.CustomItemDecoration
import com.example.myapp.adapter.MyAdapter
import com.example.myapp.adapter.VIEW_TYPE_IMAGE
import com.example.myapp.adapter.VIEW_TYPE_TEXT
import com.example.myapp.databinding.FragmentListImagesBinding
import com.example.myapp.models.UnsplashModel


class ListImagesFragment :
    BaseFragment<FragmentListImagesBinding, UnsplashViewModel>(
        R.layout.fragment_list_images,
        FragmentListImagesBinding::inflate
    ) {

    private val myAdapter by lazy {
        MyAdapter(
            likeListener = { model ->
                viewModel.updateValue(model)  //update domain model in ViewModel
            },
            itemClickListener = { model, itemImageView ->
                val extras = FragmentNavigatorExtras(itemImageView to model.id)
                navigateTo(model, extras)            //navigate to detail fragment
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition() //todo: зачем это, если ты ниже в ресайклере это делаешь
        view.doOnPreDraw { startPostponedEnterTransition() } //todo: зачем это, если ты ниже в ресайклере это делаешь
        setupRecyclerView()

        //for images
        viewModel.listLiveData.observe(viewLifecycleOwner, {
            it?.let {
                myAdapter.submitList(it)
            }
        })

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, {
            binding.pbMain.isVisible = it
        })

        viewModel.reloadBtnTvEmptyListLiveData.observe(viewLifecycleOwner, {
            binding.btnMainReload.isVisible = it
            binding.tvEmptyList.isVisible = it

        })

        viewModel.rvMainImagesLiveData.observe(viewLifecycleOwner, {
            binding.rvMainImages.isVisible = it
        })

        //for error
        viewModel.errorLiveData.observe(viewLifecycleOwner, { errorText ->
            if (errorText != null) {
                binding.apply {
                    btnMainReload.setOnClickListener {
                        reload()
                    }
                }
                Toast.makeText(requireContext(), errorText, Toast.LENGTH_SHORT).show()
            }
        })
    }

    //todo: метод этот особо не нужен, можно напрямую метод лайвдаты вызвать
    private fun reload() {
        viewModel.loadData()
    }

    private fun setupRecyclerView() {
        binding.rvMainImages.apply {
            adapter = myAdapter
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
            layoutManager = (GridLayoutManager(context, 2)).apply {
                spanSizeLookup = object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (adapter?.getItemViewType(position)) {
                            VIEW_TYPE_IMAGE -> 1
                            VIEW_TYPE_TEXT -> 2
                            else -> 1
                        }
                    }
                }
            }

            addItemDecoration(
                CustomItemDecoration(8, 8, 16, 0)
            )
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
        }
    }

    //handling double click
    private fun NavController.safeNavigate(
        direction: NavDirections,
        extras: FragmentNavigator.Extras
    ) {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(direction, extras)
        }
    }

    private fun navigateTo(model: UnsplashModel, extras: FragmentNavigator.Extras) {
        val action =
            ListImagesFragmentDirections.actionListImagesFragmentToDetailImageFragment(model)
        findNavController().safeNavigate(action, extras)
    }
}