package com.henasys.kotlinexam.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "user_email") var email: String?,
    @ColumnInfo(name = "user_token") var token: String?
)