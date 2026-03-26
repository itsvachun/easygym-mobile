package com.easygym.data.repository

import com.easygym.data.local.dao.AthleteDAO
import com.easygym.data.remote.datasource.AthleteDataSource
import com.easygym.data.remote.model.athlete.AthleteRequest
import com.easygym.data.remote.model.athlete.AthleteResponse
import com.easygym.domain.model.Athlete
import com.easygym.domain.repository.AthleteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AthleteRepositoryImpl @Inject constructor(
    private val athleteDataSource: AthleteDataSource,
    private val athleteDAO: AthleteDAO
) : AthleteRepository {

    override val athletes: Flow<List<Athlete>> = athleteDAO.getAll().map { users ->
        users.map { it.toDomain() }
    }

    override suspend fun fetch(search: String, page: Int): Result<Boolean> =
        runCatching {
            val response = athleteDataSource.fetch(search, page)
            val entities = response.content.map { it.toEntity() }
            athleteDAO.insertAll(entities)
            response.last
        }

    override suspend fun post(athleteRequest: AthleteRequest): Result<AthleteResponse> =
        runCatching {
            val response: AthleteResponse = athleteDataSource.postAthlete(athleteRequest)
            athleteDAO.insert(response.toEntity())
            response
        }
}