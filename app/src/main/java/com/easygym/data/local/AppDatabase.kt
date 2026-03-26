package com.easygym.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.easygym.data.local.dao.AthleteDAO
import com.easygym.data.local.dao.CoachDAO
import com.easygym.data.local.dao.GroupDAO
import com.easygym.data.local.dao.UserDAO
import com.easygym.data.local.entity.AthleteEntity
import com.easygym.data.local.entity.CoachEntity
import com.easygym.data.local.entity.GroupEntity
import com.easygym.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, AthleteEntity::class, CoachEntity::class, GroupEntity::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDAO: UserDAO
    abstract val athleteDAO: AthleteDAO
    abstract val coachDAO: CoachDAO
    abstract val groupDAO: GroupDAO
}
