package com.henasys.kotlinexam.di


import com.henasys.kotlinexam.data.api.UserApiTest
import com.henasys.kotlinexam.data.repository.UserDataRepositoryTest
import com.henasys.kotlinexam.presentation.MainViewModelTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class
])
interface AppComponentTest {

    fun inject(test: MainViewModelTest)
    fun inject(test: UserDataRepositoryTest)
    fun inject(test: UserApiTest)

    // Add more injects
}