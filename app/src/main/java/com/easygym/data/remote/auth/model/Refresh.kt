package com.easygym.data.remote.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface Refresh {
    @Serializable
    data class Request(@SerialName("refreshToken") val refreshToken: String) : Refresh

    @Serializable
    data class Response(
        @SerialName("accessToken") val accessToken: String,
        @SerialName("refreshToken") val refreshToken: String,
        @SerialName("tokenType") val tokenType: String,
        @SerialName("expiresIn") val expiresIn: Long,
        @SerialName("role") val role: String,
    )
}