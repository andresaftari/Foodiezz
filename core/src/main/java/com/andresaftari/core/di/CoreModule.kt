package com.andresaftari.core.di

import androidx.room.Room
import com.andresaftari.core.data.source.MealRepository
import com.andresaftari.core.data.source.local.LocalDataSource
import com.andresaftari.core.data.source.local.db.MealDB
import com.andresaftari.core.data.source.remote.RemoteDataSource
import com.andresaftari.core.data.source.remote.api.ApiService
import com.andresaftari.core.domain.repo.IMealRepository
import com.andresaftari.core.utils.CoreExecutor
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val keyPhrase: ByteArray = SQLiteDatabase.getBytes("andresaftari".toCharArray())
        val factory = SupportFactory(keyPhrase)
        Room.databaseBuilder(
            androidContext(), MealDB::class.java, "foodiezz_db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .openHelperFactory(factory)
            .build()

    }
}

val networkModule = module {
    single {
        val baseUrl = "www.themealdb.com"
        val cerificatePinnerBuilder = CertificatePinner.Builder()
            .add(baseUrl, "sha256/pz7CjjOO6yeiHWrcJ+RWljKC2pBYw+9O7XwRIl7HLn8=")
            .add(baseUrl, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
            .add(baseUrl, "sha256/Y9mvm0exBk1JoQ57f9Vm28jKo5lFm/woKcVxrYxu80o=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(cerificatePinnerBuilder)
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