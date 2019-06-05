package com.henasys.kotlinexam.data.db.entity.mapper

import com.henasys.kotlinexam.data.db.entity.UserEntity
import com.henasys.kotlinexam.model.User
import io.reactivex.Flowable

fun List<UserEntity>.toUsers(): List<User> = map {
    User(it.id, it.email, it.token)
}

fun Flowable<List<UserEntity>>.toUsers(): Flowable<List<User>> = map {
    it.toUsers()
}