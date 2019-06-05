package com.henasys.kotlinexam.data.repository

import com.henasys.kotlinexam.data.api.response.UserLogin
import com.henasys.kotlinexam.data.db.entity.UserEntity
import com.henasys.kotlinexam.model.User
import io.reactivex.Flowable
import io.reactivex.Single

interface UserRepository {
    val users: Flowable<List<User>>
    fun login(email: String, password: String): Single<UserLogin>
}