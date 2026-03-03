package com.easygym.domain.repository

import com.easygym.data.remote.auth.AuthDTO
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>
    suspend fun logout()
    suspend fun login(loginRequest: AuthDTO.Login)
    suspend fun refresh(refreshRequest: AuthDTO.Refresh)
}
