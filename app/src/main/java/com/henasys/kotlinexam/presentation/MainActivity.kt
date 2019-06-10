package com.henasys.kotlinexam.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.henasys.kotlinexam.R
import com.henasys.kotlinexam.databinding.ActivityMainBinding
import com.henasys.kotlinexam.di.ViewModelFactory
import com.henasys.kotlinexam.presentation.common.activity.BaseActivity
import com.henasys.kotlinexam.presentation.common.pref.Prefs
import com.henasys.kotlinexam.util.ext.observe
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var navigationController: NavigationController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        observeViewModel()
        setupLogoutButton()
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart")
        viewModel.start()

        if (Prefs.isNotLogined()) {
            navigationController.navigateToUserActivity()
        } else {
            binding.userEmail.text = Prefs.userEmail
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume")
    }

    private fun observeViewModel() {
        viewModel.navigateToLogoutDone.observe(this) {
            it?.getContentIfNotHandled().let {
                Timber.i("navigateToLogoutDone")
                navigationController.navigateToMainActivity()
            }
        }
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
