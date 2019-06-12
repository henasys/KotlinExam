package com.henasys.kotlinexam.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.henasys.kotlinexam.R
import com.henasys.kotlinexam.presentation.common.fragment.Findable
import com.henasys.kotlinexam.presentation.user.LoginFragment
import com.henasys.kotlinexam.presentation.user.RegistrationFragment
import com.henasys.kotlinexam.presentation.user.UserActivity
import javax.inject.Inject

class NavigationController @Inject constructor(private val activity: AppCompatActivity) {
    private val containerId: Int = R.id.content
    private val fragmentManager: FragmentManager = activity.supportFragmentManager

    private fun replaceFragment(fragment: Fragment) {
        val transaction = fragmentManager
            .beginTransaction()
            .replace(containerId, fragment, (fragment as? Findable)?.tagForFinding)

        if (fragmentManager.isStateSaved) {
            transaction.commitAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }

    fun navigateToMainActivity() {
        MainActivity.start(activity)
    }

    fun navigateToUserActivity() {
        UserActivity.start(activity)
    }

    fun navigateToLogin() {
        replaceFragment(LoginFragment.newInstance())
    }

    fun navigateToRegistration() {
        replaceFragment(RegistrationFragment.newInstance())
    }
}