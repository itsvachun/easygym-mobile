package com.easygym.domain.usecase.user

import com.easygym.domain.repository.UserRepository
import javax.inject.Inject

class FetchUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(search: String, page: Int): Result<Boolean> =
        userRepository.fetch(search, page)
}