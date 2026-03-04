package com.easygym.data.remote.datasource

import com.easygym.data.remote.model.auth.AuthResponse
import com.easygym.data.remote.model.auth.LoginRequest
import com.easygym.data.remote.model.auth.RefreshRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthDataSource {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): AuthResponse

    @POST("auth/refresh")
    suspend fun refresh(@Body refreshRequest: RefreshRequest): AuthResponse
}