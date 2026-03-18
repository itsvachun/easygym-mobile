package com.easygym.domain.repository

import com.easygym.data.remote.model.coach.CoachRequest
import com.easygym.data.remote.model.coach.CoachResponse

interface CoachRepository {
    suspend fun post(coachRequest: CoachRequest): CoachResponse


}