package com.easygym.domain.usecase.user

import com.easygym.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(search: String, page: Int): Result<Boolean> =
        userRepository.fetch(search, page)
}