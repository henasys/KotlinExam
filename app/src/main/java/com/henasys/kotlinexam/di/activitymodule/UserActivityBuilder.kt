package com.henasys.kotlinexam.di.activitymodule

import com.henasys.kotlinexam.presentation.user.UserActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface UserActivityBuilder {
    @ContributesAndroidInjector(modules = [
        UserActivityModule::class
    ])
    fun contributeActivity(): UserActivity
}
