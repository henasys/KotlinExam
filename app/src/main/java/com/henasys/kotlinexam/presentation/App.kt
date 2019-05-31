package com.henasys.kotlinexam.presentation

import com.henasys.kotlinexam.di.DaggerAppComponent
import com.henasys.kotlinexam.di.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

open class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .networkModule(NetworkModule.instance)
            .build()
    }
}