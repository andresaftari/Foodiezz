package com.andresaftari.core.di

import androidx.room.Room
import com.andresaftari.core.data.source.MealRepository
import com.andresaftari.core.data.source.local.LocalDataSource
import com.andresaftari.core.data.source.local.db.MealDB
import com.andresaftari.core.data.source.remote.RemoteDataSource
import com.andresaftari.core.data.source.remote.api.ApiService
import com.andresaftari.core.domain.repo.IMealRepository
import com.andresaftari.core.utils.CoreExecutor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MealDB>().mealDao() }
    single {
        Room.databaseBuilder(
            androidContext(), MealDB::class.java, "foodiezz_db"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    factory { CoreExecutor() }
    single<IMealRepository> { MealRepository(get(), get(), get()) }
}