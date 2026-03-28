package com.easygym.domain.usecase.event

import com.easygym.domain.model.Event
import com.easygym.domain.repository.EventRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class EventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    operator fun invoke(): Flow<List<Event>> = eventRepository.events
}