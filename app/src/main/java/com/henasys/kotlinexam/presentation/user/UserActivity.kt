package com.henasys.kotlinexam.presentation.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.henasys.kotlinexam.R
import com.henasys.kotlinexam.databinding.ActivityUserBinding
import com.henasys.kotlinexam.di.ViewModelFactory
import com.henasys.kotlinexam.presentation.NavigationController
import com.henasys.kotlinexam.presentation.common.activity.BaseActivity
import com.henasys.kotlinexam.util.ext.observe
import timber.log.Timber
import javax.inject.Inject

class UserActivity : BaseActivity() {

    @Inject lateinit var navigationController: NavigationController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: UserViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityUserBinding>(
            this, R.layout.activity_user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar.let { title = getString(R.string.activity_title_login) }

        observeViewModel()

        navigationController.navigateToLogin()
    }

    private fun observeViewModel() {
        viewModel.navigateToLoginDone.observe(this) {
            it?.getContentIfNotHandled().let {
                Timber.i("navigateToLoginDone")
                navigationController.navigateToMainActivity()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, UserActivity::class.java))
        }
    }
}
