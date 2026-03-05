package com.easygym.data.remote.datasource

import com.easygym.data.remote.model.user.UserResponse
import retrofit2.http.GET

interface UserDataSource {
    @GET("users")
    suspend fun getAll(): List<UserResponse>
}