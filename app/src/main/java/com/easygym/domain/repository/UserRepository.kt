package com.easygym.domain.repository

import com.easygym.data.remote.model.user.UpdatePasswordRequest
import com.easygym.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val users: Flow<List<User>>

    suspend fun getAll()

    suspend fun updatePassword(request: UpdatePasswordRequest): Result<Unit>
}