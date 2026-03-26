package com.easygym.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.easygym.data.local.entity.AthleteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AthleteDAO {
    @Query("SELECT * FROM athlete")
    fun getAll(): Flow<List<AthleteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(athlete: AthleteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(athletes: List<AthleteEntity>)

    @Query("DELETE FROM athlete")
    suspend fun deleteAll()
}