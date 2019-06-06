package com.henasys.kotlinexam.presentation

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.henasys.kotlinexam.R
import com.henasys.kotlinexam.databinding.ActivityMainBinding
import com.henasys.kotlinexam.di.ViewModelFactory
import com.henasys.kotlinexam.presentation.common.BaseActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        checkLogin()
    }

    fun checkLogin() {
        viewModel.user.observe(this, Observer {result ->
            when (result) {
                is Result.Failure -> {
                    Timber.i(result.e)
                }

                is Result.Success -> {
                    Timber.i("Result.Success: %s", result.data)
                }
            }

        })
    }
}
