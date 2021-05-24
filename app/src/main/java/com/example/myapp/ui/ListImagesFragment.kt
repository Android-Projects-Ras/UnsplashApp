package com.example.myapp.ui

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.example.myapp.components.ConnectivityReceiver
import com.example.myapp.components.SoundVibroService
import com.example.myapp.databinding.FragmentListImagesBinding
import com.example.myapp.models.UnsplashModel
import com.example.myapp.ui.viewmodels.MainActivityViewModel
import com.example.myapp.ui.viewmodels.UnsplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get


class ListImagesFragment :
    BaseFragment<FragmentListImagesBinding, UnsplashViewModel>(
        R.layout.fragment_list_images,
        FragmentListImagesBinding::inflate
    ) {

    private val mainActivityViewModel = get<MainActivityViewModel>()
    private val connectivityReceiver = ConnectivityReceiver()
    private val networkListener by lazy {
        NetworkStatusListener()
    }

    private val myAdapter by lazy {
        MyAdapter(
            likeListener = { model ->
                viewModel.updateValue(model)  //update domain model in ViewModel
            },
            itemClickListener = { model, itemImageView ->
                val extras = FragmentNavigatorExtras(itemImageView to model.id)
                navigateTo(model, extras)            //navigate to detail fragment
            },
            deleteItemListener = { modelId ->
                viewModel.deleteItem(modelId)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        viewModel.reloadBtnTvEmptyListVisibilityLiveData.observe(viewLifecycleOwner, {
            binding.btnMainReload.isVisible = it
            binding.tvEmptyList.isVisible = it

        })

        viewModel.rvMainVisibilityLiveData.observe(viewLifecycleOwner, {
            binding.rvMainImages.isVisible = it
        })

        //for error
        viewModel.errorLiveData.observe(viewLifecycleOwner, { errorText ->
            if (errorText != null) {
                binding.apply {
                    btnMainReload.setOnClickListener {
                        viewModel.loadData()
                    }
                }
                Toast.makeText(requireContext(), errorText, Toast.LENGTH_SHORT).show()
            }
        })
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //R.id.broadcastReceiver -> runBroadcastReceiver()
            R.id.service -> runService()
        }

        return true
    }

    fun runService() {
        val intent = Intent(requireContext(), SoundVibroService::class.java)
            requireContext().startService(intent)

    }

    /*@RequiresApi(Build.VERSION_CODES.N)
    fun runBroadcastReceiver() {
        requireContext().registerReceiver(
            connectivityReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        val intent = Intent(ConnectivityManager.CONNECTIVITY_ACTION)
        intent.putExtra("isConnected", networkListener.isConnected)
        requireContext().sendBroadcast(intent)
    }*/
}
