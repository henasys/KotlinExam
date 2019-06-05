package com.henasys.kotlinexam.data.repository

import com.henasys.kotlinexam.data.api.UserApi
import com.henasys.kotlinexam.data.api.response.UserLogin
import com.henasys.kotlinexam.data.db.UserDatabase
import com.henasys.kotlinexam.data.db.entity.mapper.toUser
import com.henasys.kotlinexam.data.db.entity.mapper.toUsers
import com.henasys.kotlinexam.model.User
import com.henasys.kotlinexam.util.rx.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val api: UserApi,
    private val schedulerProvider: SchedulerProvider,
    private val userDatabase: UserDatabase
): UserRepository {
    override val user: Flowable<User>
        = userDatabase.getAll().flatMapIterable { it }
        .firstOrError().toUser()

    override val users: Flowable<List<User>>
        = userDatabase.getAll().toUsers()

    override fun login(email: String, password: String): Single<UserLogin> {
        return api.login(email, password)
            .subscribeOn(schedulerProvider.io())
            .doOnSuccess {
                it.email = email
                userDatabase.save(it)
            }
    }
}