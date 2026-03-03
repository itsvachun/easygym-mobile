package com.easygym.services

import com.easygym.data.remote.auth.AuthDTO
import com.easygym.domain.repository.AuthRepository
import dagger.Lazy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val authRepository: Lazy<AuthRepository>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null

        val refreshToken = runBlocking {
            authRepository.get().refreshToken.first()
        } ?: return null.also {
            runBlocking { authRepository.get().logout() }
        }

        val newAccessToken = runBlocking {
            authRepository.get().refresh(AuthDTO.Refresh(refreshToken))
            authRepository.get().accessToken.first()
        }

        return response.request.newBuilder()
            .removeHeader("Authorization")
            .header("Authorization", "Bearer $newAccessToken")
            .build()
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
