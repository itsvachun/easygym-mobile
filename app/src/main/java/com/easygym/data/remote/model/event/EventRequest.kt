package com.easygym.data.remote.model.event

import com.easygym.domain.model.Event
import com.easygym.utils.enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class EventRequest(
    @SerialName("title") val title: String,
    @SerialName("eventType") val eventType: EventType,
    @SerialName("startDatetime") val startDateTime: String,
    @SerialName("endDatetime") val endDateTime: String,
    @SerialName("location") val location: String,
    @SerialName("description") val description: String,
    @SerialName("groupId") val groupId: String,
) {
    companion object {
        fun fromDomain(groupId: String, event: Event) = EventRequest(
            title = event.title,
            eventType = event.eventType,
            startDateTime = event.startDateTime.toString(),
            endDateTime = event.endDateTime.toString(),
            location = event.location,
            description = event.description,
            groupId = groupId,
        )
    }
}