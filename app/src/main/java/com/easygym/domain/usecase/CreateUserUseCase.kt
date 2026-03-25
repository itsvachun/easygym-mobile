package com.easygym.domain.usecase

import com.easygym.domain.repository.AthleteRepository
import com.easygym.domain.repository.CoachRepository
import com.easygym.ui.screens.createuser.CreateUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateUserUseCase @Inject constructor(
    private val athleteRepository: AthleteRepository,
    private val coachRepository: CoachRepository
) {
    suspend fun createUser(user: CreateUser, groupId: String) = when (user) {
        is CreateUser.Athlete -> {
            athleteRepository.post(user.toRequest(groupId))
        }

        is CreateUser.Coach -> {
            coachRepository.post(user.toRequest(groupId))
        }
    }

}