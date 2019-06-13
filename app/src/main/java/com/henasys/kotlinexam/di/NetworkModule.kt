package com.henasys.kotlinexam.di

import com.henasys.kotlinexam.data.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
open class NetworkModule {

    companion object {
        val instance = NetworkModule()
    }

    @Singleton @Provides @IntoSet
    fun provideNetworkLogger(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptors: Set<@JvmSuppressWildcards Interceptor>):
            OkHttpClient =
        OkHttpClient.Builder().apply {
            loggingInterceptors.forEach {
                addNetworkInterceptor(it)
            }
        }.build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://reqres.in/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    open fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

}