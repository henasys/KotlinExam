package com.henasys.kotlinexam.di

import android.app.Application
import com.henasys.kotlinexam.TestApp
import com.henasys.kotlinexam.presentation.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    NetworkModule::class
])
interface TestAppComponent : AndroidInjector<TestApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun networkModule(networkModule: NetworkModule): Builder
        fun build(): TestAppComponent
    }

    override fun inject(app: TestApp)
}