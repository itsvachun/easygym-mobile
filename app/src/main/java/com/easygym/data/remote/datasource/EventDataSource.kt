package com.easygym.data.remote.datasource

import com.easygym.data.remote.model.event.EventRequest
import com.easygym.data.remote.model.event.EventResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.time.Instant

interface EventDataSource {
    @GET("events")
    suspend fun fetch(
        @Query("from") from: Instant,
        @Query("to") to: Instant,
    ): List<EventResponse>

    @POST("events")
    suspend fun create(@Body event: EventRequest): EventResponse
}