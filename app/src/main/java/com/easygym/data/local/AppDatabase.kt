package com.easygym.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.easygym.data.local.dao.UserDAO
import com.easygym.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDAO: UserDAO
}
