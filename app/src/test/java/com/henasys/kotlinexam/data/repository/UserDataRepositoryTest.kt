package com.henasys.kotlinexam.data.repository

import com.henasys.kotlinexam.data.api.UserApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
class UserDataRepositoryTest {

    @Inject lateinit var api: UserApi

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun login() {

    }
}
