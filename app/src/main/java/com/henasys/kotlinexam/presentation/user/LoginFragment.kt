package com.henasys.kotlinexam.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.henasys.kotlinexam.R
import com.henasys.kotlinexam.databinding.FragmentLoginBinding
import com.henasys.kotlinexam.presentation.NavigationController
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: UserViewModel by lazy {
        ViewModelProviders.of(activity as FragmentActivity, viewModelFactory).get(UserViewModel::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigationController: NavigationController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container!!, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupOnEditorActionListener()
        setupNavigationLink()
    }

    private fun observeViewModel() {
        viewModel.emailError.observe(this, Observer {
            binding.emailWrapper.error =
                if (it) getString(R.string.invalid_email)
                else null
        })

        viewModel.PasswordError.observe(this, Observer {
            binding.passwordWrapper.error =
                if (it) getString(R.string.invalid_password)
                else null
        })
    }

    private fun setupOnEditorActionListener() {
        binding.password.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    viewModel.login()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupNavigationLink() {
        binding.forgotPasswordLink.setOnClickListener {
            navigationController.navigateToForgotPassword()
        }

        binding.registrationLink.setOnClickListener {
            navigationController.navigateToRegistration()
        }
    }

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }
}