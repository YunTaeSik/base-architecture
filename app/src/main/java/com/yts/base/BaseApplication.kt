package com.yts.base


import android.app.Application
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
/*
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(domainModule, appModule, dataModule))
        }*/
    }
}