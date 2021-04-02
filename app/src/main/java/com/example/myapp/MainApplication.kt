package com.example.myapp

import android.app.Application
import com.example.myapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Koin Android logger
            androidLogger(Level.NONE)
            //inject Android context
            androidContext(this@MainApplication)
            // use modules
            modules(
                listOf(
                    useCaseModule,
                    viewModelModule,
                    roomDatabaseModule,
                    retrofitModule,
                    repositoryModule
                )
            )
        }

    }
}
