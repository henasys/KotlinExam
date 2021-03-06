package com.henasys.kotlinexam.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.henasys.kotlinexam.di.DaggerTestAppComponentNotMock
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class NotMockUserApiTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Inject
    lateinit var userApi: UserApi

    @Before
    fun setUp() {
        DaggerTestAppComponentNotMock.builder().build().inject(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun login_not_ok() {
        val email = "tester@test.org"
        val password = "password1"

        println("email: $email")

        userApi.login(email, password)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribeBy(
                onSuccess = {println("onSuccess: $it")},
                onError = {println("onError: $it")}
            )
    }

    @Test
    fun login_ok() {
        val email = "eve.holt@reqres.in"
        val password = "pistol"

        println("email: $email")

        userApi.login(email, password)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribeBy(
                onSuccess = {println("onSuccess: $it")},
                onError = {println("onError: $it")}
            )
    }
}
