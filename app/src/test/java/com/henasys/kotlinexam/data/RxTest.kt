package com.henasys.kotlinexam.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.henasys.kotlinexam.data.db.entity.UserEntity
import com.henasys.kotlinexam.data.db.entity.mapper.toUser
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy
import org.junit.Rule
import org.junit.Test

class RxTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun rx_list_to_one() {
        val userEntities = listOf(
            UserEntity(1, "t1@test.org", "token"),
            UserEntity(2, "t2@test.org", "token")
        )
        val source: Flowable<List<UserEntity>> = Flowable.just(userEntities)
        source.flatMapIterable {it}
            .firstOrError()
            .subscribeBy {
                println("item: $it")
            }
    }

    @Test
    fun rx_empty_list() {
        val source: Flowable<List<UserEntity>> = Flowable.just(emptyList())
        source.flatMapIterable {it}
            .firstOrError()
            .subscribeBy(
                onSuccess = {println("onSuccess: $it")},
                onError = {println("onError: $it")}
            )
    }

    @Test
    fun rx_userEntity_list_to_user() {
        val source: Flowable<List<UserEntity>> = Flowable.just(emptyList())
        source.flatMapIterable {it}
            .firstOrError()
            .toUser()
            .subscribeBy(
                onSuccess = {
                    println("onSuccess: $it")},
                onError = {
                    println("onError: $it")}
            )
    }

}