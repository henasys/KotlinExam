package com.henasys.kotlinexam.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.henasys.kotlinexam.di.DaggerTestAppComponent
import io.reactivex.rxkotlin.subscribeBy
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class UserDataRepositoryTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Inject lateinit var repository: UserRepository

    @Before
    fun setUp() {
        DaggerTestAppComponent.builder().build().inject(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun login() {
        val email = "tester@test.org"
        val password = "password1"

        println("email: $email")

        repository.login(email, password)
            .doOnSuccess { it.email = email }
            .subscribeBy(
                onSuccess = {println("onSuccess: $it")},
                onError = {println("onError: $it")}
            )

    }
}
