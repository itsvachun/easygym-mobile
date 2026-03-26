package com.easygym.data.remote.datasource

import com.easygym.data.remote.model.user.UpdatePasswordRequest
import com.easygym.data.remote.model.user.UserListResponse
import com.easygym.data.remote.model.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserDataSource {
    @GET("users")
    suspend fun fetch(
        @Query("search") search: String,
        @Query("page") page: Int,
    ): UserListResponse

    @PUT("users/password-change")
    suspend fun updatePassword(@Body request: UpdatePasswordRequest): UserResponse
}