package com.easygym.domain.repository

import com.easygym.data.remote.model.athlete.AthleteRequest
import com.easygym.data.remote.model.athlete.AthleteResponse

interface AthleteRepository {

    suspend fun post(athleteRequest: AthleteRequest): AthleteResponse

}