package com.henasys.kotlinexam.di.activitymodule

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.henasys.kotlinexam.di.ViewModelKey
import com.henasys.kotlinexam.presentation.user.LoginFragment
import com.henasys.kotlinexam.presentation.user.UserActivity
import com.henasys.kotlinexam.presentation.user.UserViewModel
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

    @Binds @IntoMap
    @ViewModelKey(UserViewModel::class)
    fun bindViewModel(
        viewModel: UserViewModel
    ): ViewModel
}