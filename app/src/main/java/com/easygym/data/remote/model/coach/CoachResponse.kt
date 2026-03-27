package com.easygym.data.remote.model.coach

import com.easygym.data.local.entity.CoachEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CoachResponse(
    @SerialName("id") val id: String,
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String,
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String,
    @SerialName("enabled") val enabled: Boolean,
    @SerialName("bio") val bio: String
) {
    fun toEntity(): CoachEntity = CoachEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
        enabled = enabled,
        bio = bio
    )
}
