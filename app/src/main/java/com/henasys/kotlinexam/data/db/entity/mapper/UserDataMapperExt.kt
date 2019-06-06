package com.henasys.kotlinexam.data.db.entity.mapper

import com.henasys.kotlinexam.data.db.entity.UserEntity
import com.henasys.kotlinexam.model.User
import io.reactivex.Flowable
import io.reactivex.Single

fun List<UserEntity>.toUsers(): List<User> = map {
    User(it.id, it.email, it.token)
}

fun Flowable<List<UserEntity>>.toUsers(): Flowable<List<User>> = map {
    it.toUsers()
}

fun Flowable<UserEntity>.toUser(): Flowable<User> = map {
    User(it.id, it.email, it.token)
}

fun Single<UserEntity>.toUser(): Single<User> = map {
    User(it.id, it.email, it.token)
}