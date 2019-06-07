package com.henasys.kotlinexam.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.henasys.kotlinexam.databinding.FragmentLoginBinding
import com.henasys.kotlinexam.presentation.NavigationController
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: UserViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
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

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }
}