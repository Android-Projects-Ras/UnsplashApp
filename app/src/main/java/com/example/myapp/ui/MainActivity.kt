package com.example.myapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.components.ConnectivityReceiver
import com.example.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var connectivityReceiver: ConnectivityReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_graph) as NavHostFragment
        navController = navHostFragment.findNavController()

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(connectivityReceiver)
    }
}