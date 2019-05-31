package com.henasys.kotlinexam.data.repository

import com.henasys.kotlinexam.data.api.response.UserLogin
import io.reactivex.Single

interface UserRepository {
    fun login(email: String, password: String): Single<UserLogin>
}