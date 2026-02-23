package com.easygym.data.remote.auth

import com.easygym.data.remote.auth.model.Login
import retrofit2.http.Body
import retrofit2.http.POST

interface Auth {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: Login.Request): Login.Response
}