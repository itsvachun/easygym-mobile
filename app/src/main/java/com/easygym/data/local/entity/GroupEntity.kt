package com.easygym.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easygym.domain.model.Group
import java.util.*

@Entity(tableName = "groups")
data class GroupEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String?,
    val coachFullName: String?,
    val coachId: String?,
    val ageMin: Int?,
    val ageMax: Int?,
    val athleteCount: Int?,
    val active: Boolean,
) {
    fun toDomain(): Group = Group(
        id = id,
        name = name,
        description = description,
        coachFullName = coachFullName,
        coachId = coachId,
        ageMin = ageMin,
        ageMax = ageMax,
        athleteCount = athleteCount,
        active = active
    )
}
