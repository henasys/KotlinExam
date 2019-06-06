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

        isUserLogin()
        observeUsers()
        observeUser()
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart")
        viewModel.start()
    }

    fun isUserLogin() {
        viewModel.isUserLogin.observe(this, Observer {
            Timber.i("isUserLogin: %s", it)
        })
    }

    fun observeUsers() {
        viewModel.users.observe(this, Observer { result ->
            when (result) {
                is Result.InProgress -> {
                    Timber.i("Result.InProgress")
                }
                is Result.Success -> {
                    Timber.i("Result.Success: ${result.data}")
                }
                is Result.Failure -> {
                    Timber.i("Result.Failure: ${result.e}")
                }
            }
        })
    }

    fun observeUser() {
        viewModel.user.observe(this, Observer { result ->
            Timber.i("%s", result)
            when (result) {
                is Result.InProgress -> {
                    Timber.i("Result.InProgress")
                }
                is Result.Success -> {
                    Timber.i("Result.Success: ${result.data}")
                }
                is Result.Failure -> {
                    Timber.i("Result.Failure: ${result.e}")
                }
            }
        })
    }
}
