package com.example.myapp.ui

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.adapter.CustomItemDecoration
import com.example.myapp.adapter.MyAdapter
import com.example.myapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<UnsplashViewModel>()
    private val myAdapter by lazy { MyAdapter() }
    //private val unsplashRepository = get<UnsplashRepository>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()


        viewModel.listLiveData.observe(this, Observer {
            binding.progressBar.isVisible = true
            it?.let {

                myAdapter.setData(it)
            }
            binding.progressBar.isVisible = false
        })

        viewModel.errorLiveData.observe(this, Observer {
            if (it != null) {
                binding.recyclerView.isVisible = false
                binding.textView.isVisible = true
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
//viewM.funCalllback

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            CustomItemDecoration(8, 8, 16, 0)
        )
    }
}