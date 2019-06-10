package com.henasys.kotlinexam.presentation

import com.chibatching.kotpref.Kotpref
import com.henasys.kotlinexam.BuildConfig
import com.henasys.kotlinexam.di.DaggerAppComponent
import com.henasys.kotlinexam.di.DatabaseModule
import com.henasys.kotlinexam.di.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree


open class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        setupTimber()
        setupKotpref()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    private fun setupKotpref() {
        Kotpref.init(this.applicationContext)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .networkModule(NetworkModule.instance)
            .databaseModule(DatabaseModule.instance)
            .build()
    }

    protected open fun isInUnitTests() = false
}