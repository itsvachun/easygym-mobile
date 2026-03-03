package com.easygym.data.remote.auth

import retrofit2.http.Body
import retrofit2.http.POST

interface Auth {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: AuthDTO.Login): AuthDTO.Response

    @POST("auth/refresh")
    suspend fun refresh(@Body refreshRequest: AuthDTO.Refresh): AuthDTO.Response
}