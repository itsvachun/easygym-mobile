package com.easygym.domain.model

import com.easygym.utils.enums.UserRole
import com.easygym.utils.pagination.Searchable

open class User(
    override val firstName: String,
    override val lastName: String,
    open val email: String?,
    open val phone: String,
    open val role: UserRole,
) : Searchable