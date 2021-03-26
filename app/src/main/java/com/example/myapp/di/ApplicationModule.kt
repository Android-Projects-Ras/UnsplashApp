package com.example.myapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapp.data.cache.InternalCache
import com.example.myapp.data.cache.InternalCacheImpl
import com.example.myapp.data.cache.UnsplashDatabase
import com.example.myapp.data.cache.UnsplashModelDao
/*import com.example.myapp.data.cache.UnsplashDatabase
import com.example.myapp.data.cache.UnsplashModelDao*/
import com.example.myapp.repository.UnsplashRepository
import com.example.myapp.repository.UnsplashRepositoryImpl
import com.example.myapp.ui.UnsplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module

import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get

object ApplicationModule {

    val applicationModule: Module = module {

        single <UnsplashRepository> {
            UnsplashRepositoryImpl(get())
        }

        single <InternalCache> {
            provideInternalCacheImpl()
        }

        single {
            provideModelDao(get())
        }

        single {
            provideUnsplashDatabase(get())
        }

        viewModel {
            UnsplashViewModel(get(), get(), get())
        }
    }


    private fun provideInternalCacheImpl(): InternalCacheImpl = InternalCacheImpl()

    private fun provideModelDao(db: UnsplashDatabase) : UnsplashModelDao =
            db.getUnsplashModelDao()

    private fun provideUnsplashDatabase(context: Context): UnsplashDatabase =
            Room.databaseBuilder(context.applicationContext, UnsplashDatabase::class.java, "unsplash_database.db").build()




}