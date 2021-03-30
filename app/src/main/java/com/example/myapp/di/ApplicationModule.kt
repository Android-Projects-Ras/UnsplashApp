package com.example.myapp.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.myapp.BuildConfig
import com.example.myapp.data.api.UnsplashApi
import com.example.myapp.data.api.UnsplashApi.Companion.BASE_URL
import com.example.myapp.data.cache.InternalCache
import com.example.myapp.data.cache.InternalCacheImpl
import com.example.myapp.data.cache.UnsplashDatabase
import com.example.myapp.data.cache.UnsplashModelDao
import com.example.myapp.repository.UnsplashRepository
import com.example.myapp.repository.UnsplashRepositoryImpl
import com.example.myapp.ui.UnsplashViewModel
import com.example.myapp.usecases.GetPhotosUseCase
import com.example.myapp.usecases.GetPhotosUseCaseImpl
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApplicationModule {

    @RequiresApi(Build.VERSION_CODES.M)
    val applicationModule: Module = module {

        single {
            //provideGsonConverterFactory()
            GsonConverterFactory.create()
        }

        //creating Retrofit instance
        single {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(get<GsonConverterFactory>())
                .client(OkHttpClient.Builder().build())
                .build()
        }

        //creating API
        single { get<Retrofit>().create(UnsplashApi::class.java) }


        single <UnsplashRepository> {
            UnsplashRepositoryImpl(get(), get(), get())
        }

        single <InternalCache> {
            InternalCacheImpl()
        }

        single {
            provideModelDao(get())
        }

        single {
            provideUnsplashDatabase(get())
        }

        viewModel {
            UnsplashViewModel(get())
        }

        single<GetPhotosUseCase> {
            GetPhotosUseCaseImpl(get(), get(), get())
        }
    }

    /*private fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()*/

    private fun provideModelDao(db: UnsplashDatabase) : UnsplashModelDao =
            db.getUnsplashModelDao()

    private fun provideUnsplashDatabase(context: Context): UnsplashDatabase =
            Room.databaseBuilder(context.applicationContext, UnsplashDatabase::class.java, "unsplash_database.db").build()




}