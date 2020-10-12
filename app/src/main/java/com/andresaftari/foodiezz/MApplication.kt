package com.andresaftari.foodiezz

import android.app.Application
import com.andresaftari.core.di.databaseModule
import com.andresaftari.core.di.networkModule
import com.andresaftari.core.di.repositoryModule
import com.andresaftari.foodiezz.di.useCaseModule
import com.andresaftari.foodiezz.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}