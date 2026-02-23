package com.easygym.data.remote.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface Login {
    @Serializable
    data class Request(
        @SerialName("email") val email: String,
        @SerialName("password") val password: String
    )

    @Serializable
    data class Response(
        @SerialName("accessToken") val accessToken: String,
        @SerialName("refreshToken") val refreshToken: String,
        @SerialName("tokenType") val tokenType: String,
        @SerialName("expiresIn") val expiresIn: Long,
        @SerialName("role") val role: String,
    )
}