package com.easygym.data.remote.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface AuthDTO {
    @Serializable
    data class Login(
        @SerialName("email") val email: String,
        @SerialName("password") val password: String
    ) : AuthDTO

    @Serializable
    data class Refresh(@SerialName("refreshToken") val refreshToken: String) : AuthDTO

    @Serializable
    data class Response(
        @SerialName("accessToken") val accessToken: String,
        @SerialName("refreshToken") val refreshToken: String,
        @SerialName("tokenType") val tokenType: String,
        @SerialName("expiresIn") val expiresIn: Long,
        @SerialName("role") val role: String,
    ) : AuthDTO
}