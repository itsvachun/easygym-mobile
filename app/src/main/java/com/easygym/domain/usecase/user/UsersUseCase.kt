package com.easygym.domain.usecase.user

import com.easygym.domain.model.User
import com.easygym.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<List<User>> = userRepository.users
}