package com.example.myapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import com.example.myapp.adapter.CustomItemDecoration
import com.example.myapp.adapter.MyAdapter
import com.example.myapp.databinding.FragmentListImagesBinding
import com.example.myapp.models.UnsplashModel
import org.koin.androidx.viewmodel.ext.android.viewModel

//class ListImagesFragment : Fragment(R.layout.fragment_list_images) {
class ListImagesFragment : BaseFragment<FragmentListImagesBinding, UnsplashViewModel>(R.layout.fragment_list_images) {

    override val viewModel by viewModel<UnsplashViewModel>()
    override lateinit var binding: FragmentListImagesBinding

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
        view.doOnPreDraw { startPostponedEnterTransition() }
        postponeEnterTransition()
        binding = FragmentListImagesBinding.bind(view)
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
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                CustomItemDecoration(8, 8, 16, 0)
            )
        }
    }

    private fun navigateTo(model: UnsplashModel, extras: FragmentNavigator.Extras) {
        val action =
            ListImagesFragmentDirections.actionListImagesFragmentToDetailImageFragment(model)
        findNavController().navigate(action, extras)
    }

}