package com.easygym.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.easygym.data.local.dao.*
import com.easygym.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        AthleteEntity::class,
        CoachEntity::class,
        GroupEntity::class,
        EventEntity::class
    ],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDAO: UserDAO
    abstract val athleteDAO: AthleteDAO
    abstract val coachDAO: CoachDAO
    abstract val groupDAO: GroupDAO
    abstract val eventDAO: EventDAO
}
