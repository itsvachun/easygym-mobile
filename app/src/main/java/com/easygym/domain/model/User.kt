package com.easygym.domain.model

import com.easygym.ui.navigation.UserRole

open class User(
    override val firstName: String,
    override val lastName: String,
    open val email: String?,
    open val phone: String,
    open val role: UserRole,
) : Searchable