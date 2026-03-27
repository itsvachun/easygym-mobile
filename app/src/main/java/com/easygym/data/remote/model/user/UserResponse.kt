package com.easygym.data.remote.model.user

import com.easygym.data.local.entity.UserEntity
import com.easygym.utils.enums.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("id") val id: String,
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String,
    @SerialName("email") val email: String?,
    @SerialName("phone") val phone: String,
    @SerialName("role") val role: UserRole,
) {
    fun toEntity(): UserEntity = UserEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
        role = role
    )
}