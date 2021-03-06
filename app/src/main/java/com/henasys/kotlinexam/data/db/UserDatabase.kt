package com.henasys.kotlinexam.data.db

import androidx.annotation.CheckResult
import com.henasys.kotlinexam.data.api.response.UserLogin
import com.henasys.kotlinexam.data.db.entity.UserEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface UserDatabase {
    @CheckResult fun getAll(): Flowable<List<UserEntity>>
    fun save(response: UserLogin)
    fun deleteAll()
}