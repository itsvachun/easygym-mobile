package com.easygym.data.repository

import com.easygym.data.local.dao.EventDAO
import com.easygym.data.remote.datasource.EventDataSource
import com.easygym.data.remote.model.event.EventRequest
import com.easygym.domain.model.Event
import com.easygym.domain.repository.EventRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant

class EventRepositoryImpl @Inject constructor(
    private val eventDataSource: EventDataSource,
    private val eventDAO: EventDAO,
) : EventRepository {
    override val events: Flow<List<Event>> = eventDAO.getAll().map { events ->
        events.map { it.toDomain() }
    }

    override suspend fun fetch(from: String, to: String): Result<Unit> =
        runCatching {
            val response = eventDataSource.fetch(Instant.parse(from), Instant.parse(to))
            println("Ecco la response: $response")
            val entities = response.map { it.toEntity() }
            eventDAO.insertAll(entities)
        }

    override suspend fun create(groupId: String, event: Event): Result<Unit> =
        runCatching {
            val response = eventDataSource.create(EventRequest.fromDomain(groupId, event))
            println("Ecco la response: $response")
            eventDAO.insert(response.toEntity())
        }
}