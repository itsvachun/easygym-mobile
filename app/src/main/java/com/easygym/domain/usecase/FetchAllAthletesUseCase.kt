package com.easygym.domain.usecase

import com.easygym.domain.repository.AthleteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchAllAthletesUseCase @Inject constructor(
    private val athleteRepository: AthleteRepository
) {
    suspend operator fun invoke(): Result<Unit> = athleteRepository.fetchAll()
}