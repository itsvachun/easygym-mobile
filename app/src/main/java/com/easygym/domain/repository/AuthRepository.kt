package com.easygym.domain.repository

import com.easygym.data.remote.model.auth.LoginRequest
import com.easygym.data.remote.model.auth.RefreshRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>
    suspend fun logout()
    suspend fun login(loginRequest: LoginRequest)
    suspend fun refresh(refreshRequest: RefreshRequest)
}
