package com.henasys.kotlinexam.di

import com.henasys.kotlinexam.data.api.UserApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class NetworkModule {

    companion object {
        val instance = NetworkModule()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptors: Set<@JvmSuppressWildcards
    Interceptor>):
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
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }

    @Singleton @Provides
    open fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

}