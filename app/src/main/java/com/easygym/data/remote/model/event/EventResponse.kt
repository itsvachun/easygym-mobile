package com.easygym.data.remote.model.event

import com.easygym.data.local.entity.EventEntity
import com.easygym.utils.enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("eventType") val eventType: EventType,
    @SerialName("startDatetime") val startDateTime: String,
    @SerialName("endDatetime") val endDateTime: String,
    @SerialName("location") val location: String,
    @SerialName("description") val description: String?,
    @SerialName("groupName") val groupName: String,
    @SerialName("recurring") val recurring: Boolean,
    @SerialName("cancelled") val cancelled: Boolean,
) {
    fun toEntity(): EventEntity = EventEntity(
        id = id,
        title = title,
        eventType = eventType,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        description = description,
        location = location,
        groupName = groupName,
        recurring = recurring,
        cancelled = cancelled
    )
}