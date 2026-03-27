package com.easygym.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easygym.domain.model.User
import com.easygym.utils.enums.UserRole
import java.util.*

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val firstName: String,
    val lastName: String,
    val email: String?,
    val phone: String,
    val role: UserRole,
) {
    fun toDomain(): User = User(
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone, role = role
    )
}

