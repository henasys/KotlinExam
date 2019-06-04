package com.henasys.kotlinexam.data.db.dao

import androidx.annotation.CheckResult
import androidx.room.*
import com.henasys.kotlinexam.data.db.entity.UserEntity
import io.reactivex.Flowable

@Dao abstract class UserDao {
    @CheckResult
    @Query("SELECT * FROM user")
    abstract fun getAll(): Flowable<List<UserEntity>>

    @Query("DELETE FROM user")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(user: UserEntity)

    @Transaction
    open fun clearAndInsert(user: UserEntity) {
        deleteAll()
        insert(user)
    }
}