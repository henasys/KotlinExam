package com.henasys.kotlinexam.di

import android.app.Application
import android.content.Context
import com.henasys.kotlinexam.data.api.UserApi
import com.henasys.kotlinexam.data.db.UserDatabase
import com.henasys.kotlinexam.data.db.entity.UserRoomDatabase
import com.henasys.kotlinexam.data.repository.UserDataRepository
import com.henasys.kotlinexam.data.repository.UserRepository
import com.henasys.kotlinexam.util.rx.AppSchedulerProvider
import com.henasys.kotlinexam.util.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module internal object AppModule {
    @Singleton @Provides @JvmStatic
    fun provideContext(application: Application): Context = application

    @Singleton @Provides @JvmStatic
    fun provideUserRepository(
        api: UserApi,
        schedulerProvider: SchedulerProvider,
        userDatabase: UserDatabase
    ): UserRepository = UserDataRepository(api, schedulerProvider, userDatabase)

    @Singleton @Provides @JvmStatic
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}