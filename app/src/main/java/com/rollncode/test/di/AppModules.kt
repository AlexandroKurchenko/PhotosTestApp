package com.rollncode.test.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.rollncode.test.repoository.PhotosRepository
import com.rollncode.test.repoository.PhotosRepositoryImpl
import com.rollncode.test.repoository.db.DataBaseManager
import com.rollncode.test.repoository.db.PhotosDatabase
import com.rollncode.test.repoository.network.PhotosApi
import com.rollncode.test.ui.main.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val roomModule = module {
    single {
        val dbClass = PhotosDatabase::class.java
        Room.databaseBuilder(androidContext(), dbClass, dbClass.name).build()
    }

    single { DataBaseManager(get()) }
}
private val retrofitModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
    }
}

private val apiModule = module { single { get<Retrofit>().create(PhotosApi::class.java) } }


private val viewModels =
    module {
        single<PhotosRepository> { PhotosRepositoryImpl(get(), get()) }
        viewModel { MainViewModel(get<PhotosRepository>()) }
    }


val appModules = listOf(roomModule, retrofitModule, apiModule, viewModels)