package com.henasys.kotlinexam.di

import android.app.Application
import android.content.Context
import com.henasys.kotlinexam.data.api.UserApi
import com.henasys.kotlinexam.data.repository.UserDataRepository
import com.henasys.kotlinexam.data.repository.UserRepository
import com.henasys.kotlinexam.util.rx.SchedulerProvider
import com.henasys.kotlinexam.util.rx.TestSchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module internal object TestAppModule {
    @Singleton @Provides @JvmStatic
    fun provideContext(application: Application): Context = application

    @Singleton @Provides @JvmStatic
    fun provideUserRepository(
        api: UserApi,
        schedulerProvider: SchedulerProvider
    ): UserRepository = UserDataRepository(api, schedulerProvider)

    @Singleton @Provides @JvmStatic
    fun provideSchedulerProvider(): SchedulerProvider = TestSchedulerProvider()
}