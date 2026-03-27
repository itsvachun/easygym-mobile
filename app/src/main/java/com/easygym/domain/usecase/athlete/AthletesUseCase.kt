package com.easygym.domain.usecase.athlete

import com.easygym.domain.model.Athlete
import com.easygym.domain.repository.AthleteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AthletesUseCase @Inject constructor(
    private val athleteRepository: AthleteRepository
) {
    operator fun invoke(): Flow<List<Athlete>> = athleteRepository.athletes
}