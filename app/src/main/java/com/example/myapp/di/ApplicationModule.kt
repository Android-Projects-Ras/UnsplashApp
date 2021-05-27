package com.example.myapp.di

import androidx.room.Room
import com.example.myapp.data.api.ConnectivityInterceptor
import com.example.myapp.data.api.UnsplashApi
import com.example.myapp.data.api.UnsplashApi.Companion.BASE_URL
import com.example.myapp.data.cache.InternalCache
import com.example.myapp.data.cache.InternalCacheImpl
import com.example.myapp.data.cache.UnsplashDatabase
import com.example.myapp.repository.UnsplashRepository
import com.example.myapp.repository.UnsplashRepositoryImpl
import com.example.myapp.ui.DetailImageFragmentArgs
import com.example.myapp.ui.viewmodels.MainActivityViewModel
import com.example.myapp.ui.viewmodels.UnsplashDetailViewModel
import com.example.myapp.ui.viewmodels.UnsplashViewModel
import com.example.myapp.usecases.GetPhotosUseCase
import com.example.myapp.usecases.GetPhotosUseCaseImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val useCaseModule = module {
    factory<GetPhotosUseCase> {
        GetPhotosUseCaseImpl(get(), get(), get())
    }
}

val viewModelModule = module {

    viewModel {
        MainActivityViewModel()
    }

    viewModel {
        UnsplashViewModel(get())
    }

    viewModel { (args: DetailImageFragmentArgs) ->
        UnsplashDetailViewModel(args.unsplashModel)
    }
}

val roomDatabaseModule = module {
    single {
        get<UnsplashDatabase>().getUnsplashModelDao()
    }

    single {
        Room.databaseBuilder(androidContext(), UnsplashDatabase::class.java, "unsplash_database.db")
            .build()
    }
}

val retrofitModule = module {
    single {
        //provideGsonConverterFactory()
        GsonConverterFactory.create()
    }

    //creating Retrofit instance
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
                    .addInterceptor(ConnectivityInterceptor())
                    .build()
            )
            .build()
    }

    //creating API
    single { get<Retrofit>().create(UnsplashApi::class.java) }
}

val repositoryModule = module {
    single<UnsplashRepository> {
        UnsplashRepositoryImpl(get(), get(), get())
    }

    single<InternalCache> {
        InternalCacheImpl()
    }
}
