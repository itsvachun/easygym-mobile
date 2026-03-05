package com.easygym.domain.repository

import com.easygym.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val users: Flow<List<User>>

    suspend fun getAll()
}