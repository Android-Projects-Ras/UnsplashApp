package com.example.myapp.ui

import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.components.ConnectivityReceiver
import com.example.myapp.components.SoundVibroService
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentListImagesBinding
import com.example.myapp.ui.viewmodels.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var connectivityReceiver: ConnectivityReceiver

    val mainActivityViewModel = get<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_graph) as NavHostFragment
        navController = navHostFragment.findNavController()
        connectivityReceiver = ConnectivityReceiver()


    }

    override fun onStart() {
        super.onStart()
        registerReceiver(
            connectivityReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(connectivityReceiver)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.broadcastReceiver -> runBroadcastReceiver()
            R.id.service -> runService()
        }

        return true
    }

    private fun runService() {
        val intent = Intent(this@MainActivity, SoundVibroService::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            startService(intent)
            stopService(intent)
        }
    }

    private fun runBroadcastReceiver() {
        mainActivityViewModel.translateToast(binding)
    }
}