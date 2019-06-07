package com.henasys.kotlinexam.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.henasys.kotlinexam.databinding.FragmentLoginBinding
import com.henasys.kotlinexam.presentation.NavigationController
import com.henasys.kotlinexam.util.ProgressTimeLatch
import com.henasys.kotlinexam.util.ext.observe
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: UserViewModel by lazy {
        ViewModelProviders.of(activity!!, viewModelFactory).get(UserViewModel::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigationController: NavigationController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container!!, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLoginButton()
        setupProgress()

        testLogin()
    }

    private fun testLogin() {
        val email = "eve.holt@reqres.in"
        val password = "pistol"

        binding.email.setText(email)
        binding.password.setText(password)
    }

    private fun setupProgress() {
        val progressTimeLatch = ProgressTimeLatch {
            binding.progress.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressTimeLatch.loading = isLoading ?: false
        }
    }

    private fun setupLoginButton() {
        binding.login.apply {
            setOnClickListener {
                viewModel.login(binding.email.text.toString(), binding.password.text.toString())
            }
        }
    }

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }
}