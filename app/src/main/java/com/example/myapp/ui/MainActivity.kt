package com.example.myapp.ui

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.components.ConnectivityReceiver
import com.example.myapp.data.api.NetworkStatusListener
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.ui.viewmodels.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var connectivityReceiver: ConnectivityReceiver
    private val networkListener by lazy {
        NetworkStatusListener(this)
    }
    private val mainActivityViewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_graph) as NavHostFragment
        navController = navHostFragment.findNavController()


        mainActivityViewModel.toastTextLiveData.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()
        registerBroadcast()
        networkListener.register(getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager)
    }

    private fun registerBroadcast() {
        connectivityReceiver = ConnectivityReceiver(receiverListener = {
            binding.viewCustomToast.setText(it)
            binding.viewCustomToast.translateToast()
        })
        val intentFilter = IntentFilter("ACTION_CONNECTION_CHANGE")
        registerReceiver(connectivityReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(connectivityReceiver)
        networkListener.unregister(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    }
}

