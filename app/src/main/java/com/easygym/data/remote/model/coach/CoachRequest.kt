package com.easygym.data.remote.model.coach

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoachRequest(
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String,
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String,
    @SerialName("bio") val bio: String,
    @SerialName("password") val password: String,
    @SerialName("groupId") val groupId: String
)
