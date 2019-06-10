package com.henasys.kotlinexam.di.activitymodule

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.henasys.kotlinexam.di.ViewModelKey
import com.henasys.kotlinexam.presentation.MainActivity
import com.henasys.kotlinexam.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {
    @Binds
    fun providesAppCompatActivity(mainActivity: MainActivity): AppCompatActivity

    @Binds @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindViewModel(
        viewModel: MainViewModel
    ): ViewModel
}