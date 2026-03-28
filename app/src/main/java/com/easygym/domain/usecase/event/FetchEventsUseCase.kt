package com.easygym.domain.usecase.event

import com.easygym.domain.repository.EventRepository
import jakarta.inject.Inject

class FetchEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(from: String, to: String): Result<Unit> {
        println("FetchEventsUseCase")
        return eventRepository.fetch(from, to)
    }
}