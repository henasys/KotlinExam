package com.henasys.kotlinexam.presentation.user


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.henasys.kotlinexam.R
import com.henasys.kotlinexam.databinding.FragmentForgotPasswordBinding
import com.henasys.kotlinexam.presentation.NavigationController
import com.henasys.kotlinexam.util.ext.observe
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class ForgotPasswordFragment : DaggerFragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: UserViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigationController: NavigationController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container!!, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = getString(R.string.activity_title_forgot_password)

        observeViewModel()
        setupNavigationLink()
    }

    private fun observeViewModel() {
        viewModel.navigateToLoginDone.observe(this) {
            it?.getContentIfNotHandled()?.let {
                Timber.i("navigateToLoginDone")
                navigationController.navigateToMainActivity()
            }
        }

        viewModel.emailError.observe(this, Observer {
            binding.emailWrapper.error =
                if (it) getString(R.string.invalid_email)
                else null
        })
    }

    private fun setupNavigationLink() {
        binding.loginLink.setOnClickListener {
            navigationController.navigateToLogin()
        }
    }

    companion object {
        fun newInstance(): ForgotPasswordFragment = ForgotPasswordFragment()
    }
}
