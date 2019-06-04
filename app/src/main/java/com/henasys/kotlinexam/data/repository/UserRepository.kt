package com.henasys.kotlinexam.data.repository

import com.henasys.kotlinexam.data.api.response.UserLogin
import com.henasys.kotlinexam.data.db.entity.UserEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface UserRepository {
    val users: Flowable<List<UserEntity>>
    fun login(email: String, password: String): Single<UserLogin>
}