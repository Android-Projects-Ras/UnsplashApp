package com.example.myapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.adapter.CustomItemDecoration
import com.example.myapp.adapter.MyAdapter
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.models.UnsplashModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<UnsplashViewModel>()
    private val myAdapter by lazy {
        MyAdapter(
            likeListener = { model ->
                viewModel.updateValue(model)  //update domain model in ViewModel
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        //for images
        viewModel.listLiveData.observe(this, Observer {
            it?.let {
                myAdapter.updateList(it)
            }
        })

        //for progress bar
        viewModel.isLoadingLiveData.observe(this, Observer {
            binding.pbMain.isVisible = it
        })

        //for error
        viewModel.errorLiveData.observe(this, Observer {
            if (it != null) {
                binding.rvMainImages.isVisible = false
                binding.tvItemText.isVisible = true
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
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
}