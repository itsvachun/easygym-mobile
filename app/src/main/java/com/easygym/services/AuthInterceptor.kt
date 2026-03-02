package com.easygym.services

import com.easygym.data.remote.auth.model.Refresh
import com.easygym.domain.repository.AuthRepository
import dagger.Lazy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val authRepository: Lazy<AuthRepository>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            authRepository.get().accessToken.first()
        }

        val request = chain.request()

        val authenticatedRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        var response = chain.proceed(authenticatedRequest)

        if (response.code == 401) {

            println("sono dentro interceptor")

            response.close()

            val refreshToken = runBlocking {
                authRepository.get().refreshToken.first()
            }

            if (refreshToken != null) {
                val newAccessToken = runBlocking {
                    authRepository.get().refresh(Refresh.Request(refreshToken))
                    authRepository.get().accessToken.first()
                }

                val retriedRequest = request.newBuilder()
                    .removeHeader("Authorization")
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()

                response = chain.proceed(retriedRequest)
            }
        } else {
            runBlocking {
                authRepository.get().logout()
            }
        }
        return response
    }
}
