package com.easygym.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easygym.domain.model.Event
import com.easygym.utils.enums.EventType
import java.time.Instant

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val eventType: EventType,
    val startDateTime: String,
    val endDateTime: String,
    val location: String,
    val description: String?,
    val groupName: String,
    val recurring: Boolean,
    val cancelled: Boolean,
) {
    fun toDomain(): Event = Event(
        title = title,
        eventType = eventType,
        description = description ?: "Descrizione non disponibile",
        startDateTime = Instant.parse(startDateTime),
        endDateTime = Instant.parse(endDateTime),
        location = location,
        groupName = groupName,
        cancelled = cancelled
    )
}