package com.henasys.kotlinexam.di


import com.henasys.kotlinexam.data.api.MockUserApiTest
import com.henasys.kotlinexam.data.repository.UserDataRepositoryTest
import com.henasys.kotlinexam.presentation.MainViewModelTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    TestAppModule::class,
    TestNetworkModule::class
])
interface TestAppComponent {

    fun inject(test: MainViewModelTest)
    fun inject(test: UserDataRepositoryTest)
    fun inject(test: MockUserApiTest)

    // Add more injects
}