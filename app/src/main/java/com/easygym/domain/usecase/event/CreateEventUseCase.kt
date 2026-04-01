package com.easygym.domain.usecase.event

import com.easygym.domain.model.Event
import com.easygym.domain.repository.EventRepository
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(groupId: String, event: Event): Result<Unit> =
        eventRepository.create(groupId, event)
}