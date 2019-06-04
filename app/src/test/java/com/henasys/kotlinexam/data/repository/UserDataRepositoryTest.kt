package com.henasys.kotlinexam.data.repository

import com.henasys.kotlinexam.data.api.UserApi
import com.henasys.kotlinexam.data.api.response.UserLogin
import com.henasys.kotlinexam.di.DaggerAppComponentTest
import com.henasys.kotlinexam.util.rx.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

class UserDataRepositoryTest {

    @Inject lateinit var repository: UserRepository

    @Before
    fun setUp() {
        DaggerAppComponentTest.builder().build().inject(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun login() {
        val email = "tester@test.org"
        val password = "password1"
        repository.login(email, password)
            .observeOn(Schedulers.trampoline())
            .doOnSuccess { t -> t.email = email }

    }
}
