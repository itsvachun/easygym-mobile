package com.easygym.data.remote.model.group

import com.easygym.data.local.entity.GroupEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GroupResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String?,
    @SerialName("coachFullName") val coachFullName: String?,
    @SerialName("coachId") val coachId: String?,
    @SerialName("ageMin") val ageMin: Int?,
    @SerialName("ageMax") val ageMax: Int?,
    @SerialName("athleteCount") val athleteCount: Int?,
    @SerialName("active") val active: Boolean,
) {
    fun toEntity(): GroupEntity = GroupEntity(
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
