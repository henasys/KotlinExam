package com.henasys.kotlinexam

import com.chibatching.kotpref.Kotpref
import com.henasys.kotlinexam.presentation.App
import org.robolectric.RuntimeEnvironment

class TestApp : App() {

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(RuntimeEnvironment.application)
    }

    override fun isInUnitTests(): Boolean {
        return true
    }
}
