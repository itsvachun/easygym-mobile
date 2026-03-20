package com.easygym.utils

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

        val request = chain.request().newBuilder()
            .apply {
                accessToken?.let {
                    header("Authorization", "Bearer $it")
                }
            }
            .build()

        return chain.proceed(request)
    }
}
