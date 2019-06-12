package com.henasys.kotlinexam.di.activitymodule

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.henasys.kotlinexam.di.ViewModelKey
import com.henasys.kotlinexam.presentation.user.*
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface UserActivityModule {
    @Binds
    fun providesAppCompatActivity(activity: UserActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    fun contributeForgotPasswordFragment(): ForgotPasswordFragment

    @ContributesAndroidInjector
    fun contributeRegistrationFragment(): RegistrationFragment

    @Binds @IntoMap
    @ViewModelKey(UserViewModel::class)
    fun bindViewModel(
        viewModel: UserViewModel
    ): ViewModel
}