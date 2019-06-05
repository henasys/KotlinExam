package com.henasys.kotlinexam.di

import android.app.Application
import android.content.Context
import com.henasys.kotlinexam.TestApp
import com.henasys.kotlinexam.data.api.UserApi
import com.henasys.kotlinexam.data.db.UserDatabase
import com.henasys.kotlinexam.data.db.entity.UserEntity
import com.henasys.kotlinexam.data.repository.UserDataRepository
import com.henasys.kotlinexam.data.repository.UserRepository
import com.henasys.kotlinexam.util.rx.SchedulerProvider
import com.henasys.kotlinexam.util.rx.TestSchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.Flowable
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import javax.inject.Singleton

@Module internal object TestAppModule {

    @Singleton @Provides @JvmStatic
    fun provideUserRepository(
        api: UserApi,
        schedulerProvider: SchedulerProvider,
        userDatabase: UserDatabase
    ): UserRepository = UserDataRepository(api, schedulerProvider, userDatabase)

    @Singleton @Provides @JvmStatic
    fun provideSchedulerProvider(): SchedulerProvider = TestSchedulerProvider()

    @Singleton @Provides @JvmStatic
    fun provideUserDatabase(): UserDatabase {
        val userEntities = listOf(UserEntity(0, "t@test.org", "token"))
        val userDatabase = mock(UserDatabase::class.java)
        `when`(userDatabase.getAll()).thenReturn(Flowable.just(userEntities))
        return userDatabase
    }
}