package com.easygym.data.repository

import com.easygym.data.local.dao.CoachDAO
import com.easygym.data.remote.datasource.CoachDataSource
import com.easygym.data.remote.model.coach.CoachRequest
import com.easygym.data.remote.model.coach.CoachResponse
import com.easygym.domain.repository.CoachRepository
import javax.inject.Inject

class CoachRepositoryImpl @Inject constructor(
    val coachDataSource: CoachDataSource,
    val coachDAO: CoachDAO
) : CoachRepository {
    override suspend fun post(coachRequest: CoachRequest): CoachResponse {
        val response: CoachResponse = coachDataSource.postCoach(coachRequest)
        coachDAO.insertCoach(response.toEntity())
        return response
    }

}