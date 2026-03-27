package com.easygym.domain.usecase.auth

import com.easygym.data.remote.model.auth.RefreshRequest
import com.easygym.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RefreshSessionUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.refreshToken.first()?.let {
        authRepository.refresh(RefreshRequest(it))
    }
}
