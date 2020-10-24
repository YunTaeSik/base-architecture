package com.yts.base.di.koinmodule

import android.os.Build
import androidx.room.Room
import com.yts.base.util.Const
import com.yts.base.util.TLSSocketFactory
import com.yts.data.source.local.AppDatabase
import com.yts.data.source.remote.SearchService
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

val dataModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Const.BASE_URL)
            .client(get())
            .build()
    }
    single<OkHttpClient> {
        val okHttpClientBuilder = OkHttpClient.Builder()

        okHttpClientBuilder.connectTimeout(Const.NETWORK_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Const.NETWORK_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Const.NETWORK_WRITE_TIMEOUT, TimeUnit.SECONDS)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            okHttpClientBuilder.sslSocketFactory(TLSSocketFactory, get()) //SSLProtocolException 처리
        }

        okHttpClientBuilder.build()
    }
    single {
        val trustManagerFactory: TrustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)
        val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            "Unexpected default trust managers:" + trustManagers.contentToString()
        }
        val trustManager: X509TrustManager = trustManagers[0] as X509TrustManager
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
        trustManager
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }



    single<SearchService> {
        get<Retrofit>().create(SearchService::class.java)
    }
}
