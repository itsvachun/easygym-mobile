package com.easygym.data.remote.auth

import com.easygym.data.remote.auth.model.Login
import com.easygym.data.remote.auth.model.Refresh
import retrofit2.http.Body
import retrofit2.http.POST

interface Auth {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: Login.Request): Login.Response

    @POST("auth/refresh")
    suspend fun refresh(@Body refreshRequest: Refresh.Request): Refresh.Response
}