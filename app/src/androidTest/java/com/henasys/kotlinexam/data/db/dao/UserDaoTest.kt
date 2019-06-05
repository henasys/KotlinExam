package com.henasys.kotlinexam.data.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.henasys.kotlinexam.data.db.AppDatabase
import com.henasys.kotlinexam.data.db.entity.UserEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val email = "tester@test.org"
        val token = "token_for_testing"
        val user = UserEntity(0, email, token)

        userDao.insert(user)

        userDao.getAll()
            .test()
            .assertValue {
                it.size == 1
            }
            .assertValue {
                it.get(0).email == email
            }
    }
}