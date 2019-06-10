package com.henasys.kotlinexam.presentation.common.pref

import com.chibatching.kotpref.KotprefModel

object Prefs : KotprefModel() {
    var userEmail by nullableStringPref()
    var userToken by nullableStringPref()

    fun login(email: String, token: String) {
        userEmail = email
        userToken = token
    }

    fun logout() {
        userEmail = null
        userToken = null
    }

    fun isLogined(): Boolean {
        return !isNotLogined()
    }

    fun isNotLogined(): Boolean {
        return userToken.isNullOrBlank()
    }
}