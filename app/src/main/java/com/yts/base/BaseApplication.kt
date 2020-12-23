package com.yts.base


import androidx.multidex.MultiDexApplication
import com.yts.base.di.koinmodule.appModule
import com.yts.base.di.koinmodule.dataModule
import com.yts.base.di.koinmodule.domainModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


@HiltAndroidApp
class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(domainModule, appModule, dataModule))
        }
    }
}