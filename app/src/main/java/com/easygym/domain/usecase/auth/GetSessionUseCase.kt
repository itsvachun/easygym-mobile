package com.easygym.domain.usecase.auth

import com.easygym.domain.model.UserSession
import com.easygym.domain.repository.AuthRepository
import com.easygym.utils.authentication.TokenDecoder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenDecoder: TokenDecoder
) {
    operator fun invoke(): Flow<UserSession> = authRepository.accessToken.map { token ->
        UserSession(
            role = tokenDecoder.getRoleFromToken(token),
            isExpired = tokenDecoder.isTokenExpired(token),
            accessToken = token
        )
    }
}
