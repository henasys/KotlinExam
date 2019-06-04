package com.henasys.kotlinexam.data.api.response.mapper

import com.henasys.kotlinexam.data.api.response.UserLogin
import com.henasys.kotlinexam.data.db.entity.UserEntity

fun UserLogin.toUserEntity(): UserEntity {
    return UserEntity(
        id = 0,
        email = email,
        token = token
    )
}