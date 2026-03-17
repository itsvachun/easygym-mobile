package com.easygym.domain.usecase

import com.easygym.domain.repository.UserRepository
import com.easygym.ui.screens.changepassword.UpdatePassword
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdatePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(updatePassword: UpdatePassword): Result<Unit> =
        userRepository.updatePassword(request = updatePassword.toRequest())
}