package com.easygym.domain.model

import com.easygym.ui.navigation.UserRole

data class User(
    val firstName: String,
    val lastName: String,
    val email: String?,
    val phone: String,
    val role: UserRole,
)