package com.easygym.domain.usecase.athlete

import com.easygym.domain.repository.AthleteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchAthletesUseCase @Inject constructor(
    private val athleteRepository: AthleteRepository
) {
    suspend operator fun invoke(search: String, page: Int): Result<Boolean> =
        athleteRepository.fetch(search, page)
}