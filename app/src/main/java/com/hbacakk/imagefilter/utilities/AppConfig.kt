package com.hbacakk.imagefilter.utilities

import android.app.Application
import com.hbacakk.imagefilter.di.repositoryModule
import com.hbacakk.imagefilter.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class AppConfig :Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}