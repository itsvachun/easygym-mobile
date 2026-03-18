package com.easygym.data.remote.datasource

import com.easygym.data.remote.model.coach.CoachRequest
import com.easygym.data.remote.model.coach.CoachResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CoachDataSource {

    @POST("coaches")
    suspend fun postCoach(@Body coachRequest: CoachRequest): CoachResponse
}