package com.easygym.domain.repository

import com.easygym.data.remote.auth.model.Login
import com.easygym.data.remote.auth.model.Refresh
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>
    suspend fun logout()
    suspend fun login(loginRequest: Login.Request)
    suspend fun refresh(refreshRequest: Refresh.Request)
}
