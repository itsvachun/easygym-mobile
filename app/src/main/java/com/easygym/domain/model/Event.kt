package com.easygym.domain.model

import com.easygym.utils.enums.EventType
import java.time.Instant

data class Event(
    val title: String,
    val eventType: EventType,
    val description: String,
    val startDateTime: Instant,
    val endDateTime: Instant,
    val location: String,
    val groupName: String,
    val cancelled: Boolean
)