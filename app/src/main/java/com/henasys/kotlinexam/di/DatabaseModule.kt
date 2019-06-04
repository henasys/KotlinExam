package com.henasys.kotlinexam.di

import android.app.Application
import androidx.room.Room
import com.henasys.kotlinexam.data.db.AppDatabase
import com.henasys.kotlinexam.data.db.UserDatabase
import com.henasys.kotlinexam.data.db.dao.UserDao
import com.henasys.kotlinexam.data.db.entity.UserRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DatabaseModule {

    companion object {
        val instance = DatabaseModule()
    }

    @Singleton @Provides
    fun provideUserDatabase(db: AppDatabase, dao: UserDao): UserDatabase =
        UserRoomDatabase(db, dao)

    @Singleton @Provides
    open fun provideDb(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()
}