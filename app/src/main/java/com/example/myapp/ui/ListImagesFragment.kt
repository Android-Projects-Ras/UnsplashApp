package com.example.myapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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

class ListImagesFragment: Fragment(R.layout.fragment_list_images) {

    private var _binding :FragmentListImagesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<UnsplashViewModel>()
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
        _binding = FragmentListImagesBinding.bind(view)
        setupRecyclerView()

        //for images
        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                myAdapter.submitList(it)
            }

        })

        //for progress bar
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbMain.isVisible = it
        })

        //for error
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.apply {
                    rvMainImages.isVisible = false
                    tvEmptyList.isVisible = true
                    btnMainReload.isVisible = true
                    btnMainReload.setOnClickListener {
                        reload()
                    }
                }
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun reload() {
        binding.btnMainReload.isVisible = false //todo: изменение видимости должно быть сделано через лайвдату и управляться во вью модели
        binding.tvEmptyList.isVisible = false
        viewModel.loadData()
        binding.rvMainImages.isVisible = true

    }

    private fun setupRecyclerView() {
        binding.rvMainImages.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                CustomItemDecoration(8, 8, 16, 0)
            )
        }
    }

    private fun navigateTo(model: UnsplashModel, extras: FragmentNavigator.Extras) {
        val action = ListImagesFragmentDirections.actionListImagesFragmentToDetailImageFragment(model)
        findNavController().navigate(action, extras)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}