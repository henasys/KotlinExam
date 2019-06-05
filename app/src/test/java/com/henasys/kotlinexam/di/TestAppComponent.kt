package com.henasys.kotlinexam.di


import com.henasys.kotlinexam.data.api.UserApiTest
import com.henasys.kotlinexam.data.repository.UserDataRepositoryTest
import com.henasys.kotlinexam.presentation.MainViewModelTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    TestAppModule::class,
    NetworkModule::class
])
interface TestAppComponent {

    fun inject(test: MainViewModelTest)
    fun inject(test: UserDataRepositoryTest)
    fun inject(test: UserApiTest)

    // Add more injects
}