package com.henasys.kotlinexam.presentation.user


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.henasys.kotlinexam.R
import com.henasys.kotlinexam.databinding.FragmentForgotPasswordBinding
import com.henasys.kotlinexam.presentation.NavigationController
import dagger.android.support.DaggerFragment
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
