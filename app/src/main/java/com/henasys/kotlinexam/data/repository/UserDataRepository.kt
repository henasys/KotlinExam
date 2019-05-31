package com.henasys.kotlinexam.data.repository

import com.henasys.kotlinexam.data.api.UserApi
import com.henasys.kotlinexam.data.api.response.UserLogin
import com.henasys.kotlinexam.util.rx.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val api: UserApi,
    private val schedulerProvider: SchedulerProvider
): UserRepository {
    override fun login(email: String, password: String): Single<UserLogin> {
        return api.login(email, password).subscribeOn(schedulerProvider.io())
    }
}