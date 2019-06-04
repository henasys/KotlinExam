package com.henasys.kotlinexam.presentation

import com.henasys.kotlinexam.di.DaggerAppComponentTest
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import javax.inject.Inject

class MainViewModelTest {

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        DaggerAppComponentTest.builder().build().inject(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun start() {
        mainViewModel.start()
    }
}