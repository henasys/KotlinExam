package com.henasys.kotlinexam.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.henasys.kotlinexam.data.db.dao.UserDao
import com.henasys.kotlinexam.data.db.entity.UserEntity

@Database(
    entities = [
        (UserEntity::class)
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val NAME = "APP_DATABASE"
    }

    abstract fun userDao(): UserDao
}