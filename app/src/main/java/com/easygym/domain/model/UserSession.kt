package com.easygym.domain.model

import com.easygym.utils.enums.UserRole

data class UserSession(
    val role: UserRole? = null,
    val isExpired: Boolean = true,
    val accessToken: String? = null
)
