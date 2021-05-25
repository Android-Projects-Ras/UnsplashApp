package com.example.myapp.ui

import android.content.*
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.components.ConnectivityReceiver
import com.example.myapp.components.SoundVibroService
import com.example.myapp.data.api.NetworkStatusListener
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.ui.viewmodels.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var connectivityReceiver: ConnectivityReceiver
    private lateinit var soundVibroService: SoundVibroService
    private val networkListener by lazy {
        NetworkStatusListener()
    }
    private val mainActivityViewModel = get<MainActivityViewModel>()


    var bound = false
    val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as SoundVibroService.SoundVibroServiceBinder
            soundVibroService = binder.getSoundVibroService()
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_graph) as NavHostFragment
        navController = navHostFragment.findNavController()

        mainActivityViewModel.toastTextLiveData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()
        /**binding service*/
        Intent(this, SoundVibroService::class.java).also {
            bindService(it, connection, Context.BIND_AUTO_CREATE)
        }
        connectivityReceiver = ConnectivityReceiver()
        networkListener.register(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        unregisterReceiver(connectivityReceiver)
        networkListener.unregister(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    }
}

