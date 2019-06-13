package com.henasys.kotlinexam.di

import com.henasys.kotlinexam.data.api.NotMockUserApiTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class
])
interface TestAppComponentNotMock {
    fun inject(test: NotMockUserApiTest)
}