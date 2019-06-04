package com.henasys.kotlinexam.data.db.entity

import androidx.annotation.CheckResult
import androidx.room.RoomDatabase
import com.henasys.kotlinexam.data.api.response.UserLogin
import com.henasys.kotlinexam.data.api.response.mapper.toUserEntity
import com.henasys.kotlinexam.data.db.UserDatabase
import com.henasys.kotlinexam.data.db.dao.UserDao
import io.reactivex.Flowable
import javax.inject.Inject

class UserRoomDatabase @Inject constructor(
    private val database: RoomDatabase,
    private val userDao: UserDao
) : UserDatabase {
    @CheckResult
    override fun getAll(): Flowable<List<UserEntity>> {
        return userDao.getAll()
    }

    override fun save(response: UserLogin) {
        database.runInTransaction {
            userDao.clearAndInsert(response.toUserEntity())
        }
    }

}