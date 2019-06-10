package com.henasys.kotlinexam.di.activitymodule

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.henasys.kotlinexam.di.ViewModelKey
import com.henasys.kotlinexam.presentation.MainActivity
import com.henasys.kotlinexam.presentation.user.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {
    @Binds
    fun providesAppCompatActivity(mainActivity: MainActivity): AppCompatActivity

    @Binds @IntoMap
    @ViewModelKey(UserViewModel::class)
    fun bindViewModel(
        viewModel: UserViewModel
    ): ViewModel
}