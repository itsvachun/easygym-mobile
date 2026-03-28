package com.easygym.domain.repository

import com.easygym.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    val events: Flow<List<Event>>

    suspend fun fetch(from: String, to: String): Result<Unit>
}