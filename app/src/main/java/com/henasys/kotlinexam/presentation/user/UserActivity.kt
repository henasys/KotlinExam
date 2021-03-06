package com.henasys.kotlinexam.presentation.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.henasys.kotlinexam.R
import com.henasys.kotlinexam.databinding.ActivityUserBinding
import com.henasys.kotlinexam.presentation.NavigationController
import com.henasys.kotlinexam.presentation.common.activity.BaseActivity
import javax.inject.Inject

class UserActivity : BaseActivity() {

    @Inject lateinit var navigationController: NavigationController

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityUserBinding>(
            this, R.layout.activity_user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        navigationController.navigateToLogin()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, UserActivity::class.java))
        }
    }
}
