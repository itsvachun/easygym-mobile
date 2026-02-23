package com.easygym.domain.repository

import com.easygym.data.remote.auth.model.Login
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun jwtToken(): Flow<String?>
    suspend fun saveJwtToken(token: String)
    suspend fun clearJwtToken()
    suspend fun login(loginRequest: Login.Request)
}
