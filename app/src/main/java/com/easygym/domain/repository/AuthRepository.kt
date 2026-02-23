package com.easygym.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun jwtToken(): Flow<String?>
    suspend fun saveJwtToken(token: String)
    suspend fun clearJwtToken()
}
