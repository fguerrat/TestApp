package com.lacalera.testapp.di.module

import android.app.Application
import android.content.Context
import com.lacalera.testapp.BuildConfig
import com.lacalera.testapp.data.remote.apis.RickandMortyApi
import com.lacalera.testapp.data.remote.interceptor.BaseUrlInterceptor
import com.lacalera.testapp.data.remote.service.RickandMortyServiceApi
import com.lacalera.testapp.di.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @ApplicationContext
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideBaseUrlInterceptor(): BaseUrlInterceptor {
        return BaseUrlInterceptor().apply {
            updateBaseUrl(BuildConfig.HOST_API_RICKANDMORTY)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        baseUrlInterceptor: BaseUrlInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(baseUrlInterceptor)
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.HOST_API_RICKANDMORTY)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRickAndMortyApi(retrofit: Retrofit): RickandMortyApi {
        return retrofit.create(RickandMortyApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideRickandMortyServiceApi(
        @ApplicationContext context: Context,
        rickandMortyApi: RickandMortyApi
    ): RickandMortyServiceApi {
        return RickandMortyServiceApi(context, rickandMortyApi)
    }
}