package com.henasys.kotlinexam.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.henasys.kotlinexam.R
import com.henasys.kotlinexam.databinding.ActivityMainBinding
import com.henasys.kotlinexam.di.ViewModelFactory
import com.henasys.kotlinexam.presentation.common.activity.BaseActivity
import com.henasys.kotlinexam.presentation.user.UserViewModel
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var navigationController: NavigationController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: UserViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        binding.viewModel = viewModel

        observeUser()
        setupLogoutButton()
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart")
        viewModel.start()
    }

    private fun observeUser() {
        viewModel.user.observe(this, Observer {
            Timber.i("%s", it)
            when (it) {
                is Result.Success -> {
                    binding.userEmail.text = it.data.email
                }

                is Result.Failure -> {
                    navigationController.navigateToUserActivity()
                }
            }
        })
    }

    private fun setupLogoutButton() {
        binding.logoutButton.apply {
            setOnClickListener {
                viewModel.logout()
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, MainActivity::class.java)

        fun start(context: Context) {
            createIntent(context).let {
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(it)
            }
        }
    }
}
