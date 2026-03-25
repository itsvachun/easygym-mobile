package com.easygym.domain.repository

import com.easygym.data.remote.model.athlete.AthleteRequest
import com.easygym.data.remote.model.athlete.AthleteResponse
import com.easygym.domain.model.Athlete
import kotlinx.coroutines.flow.Flow

interface AthleteRepository {
    val athletes: Flow<List<Athlete>>
    suspend fun fetchAll(): Result<Unit>
    suspend fun post(athleteRequest: AthleteRequest): Result<AthleteResponse>
}