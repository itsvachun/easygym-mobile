package com.easygym.domain.model

import com.easygym.ui.navigation.UserRole

open class User(
    open val firstName: String,
    open val lastName: String,
    open val email: String?,
    open val phone: String,
    open val role: UserRole,
)