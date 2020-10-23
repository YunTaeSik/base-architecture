package com.yts.base.di.koinmodule

import com.yts.base.util.Const
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

var dataModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Const.BASE_URL)
            .client(get())
            .build()
    }
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(Const.NETWORK_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Const.NETWORK_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Const.NETWORK_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}
