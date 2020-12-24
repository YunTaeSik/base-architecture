package com.yts.base.di.hilt

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yts.base.util.Const
import com.yts.base.util.TLSSocketFactory
import com.yts.data.source.local.AppDatabase
import com.yts.data.source.remote.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(ApplicationComponent::class) //해당 모듈은 어플리케이션 라이프타임을 따른다.
object DataModule {

    @Singleton
    @Provides
    fun provideX509TrustManager(): X509TrustManager {
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
        return trustManager
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(x509TrustManager: X509TrustManager): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        okHttpClientBuilder.connectTimeout(Const.NETWORK_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Const.NETWORK_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Const.NETWORK_WRITE_TIMEOUT, TimeUnit.SECONDS)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            okHttpClientBuilder.sslSocketFactory(
                TLSSocketFactory,
                x509TrustManager
            ) //SSLProtocolException 처리
        }

        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Const.BASE_URL)
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideSearchService(retrofit: Retrofit):SearchService = retrofit.create(SearchService::class.java)
}