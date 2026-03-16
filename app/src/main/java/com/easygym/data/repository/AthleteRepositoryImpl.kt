package com.easygym.data.repository

import com.easygym.data.local.dao.AthleteDAO
import com.easygym.data.local.entity.AthleteEntity
import com.easygym.data.remote.datasource.AthleteDataSource
import com.easygym.data.remote.model.athlete.AthleteRequest
import com.easygym.data.remote.model.athlete.AthleteResponse
import com.easygym.domain.repository.AthleteRepository
import javax.inject.Inject

class AthleteRepositoryImpl @Inject constructor(
    private val athleteDataSource: AthleteDataSource,
    private val athleteDAO: AthleteDAO
) :
    AthleteRepository {

    override suspend fun post(athleteRequest: AthleteRequest): AthleteResponse {
        val response: AthleteResponse = athleteDataSource.postAthlete(athleteRequest)
        AthleteEntity(
            id = response.id,
            firstName = response.firstName,
            lastName = response.lastName,
            email = response.email,
            phone = response.phone,
            enabled = response.enabled,
            birthDate = response.birthDate.toString(),
            taxCode = response.taxCode,
            address = response.address,
            city = response.city,
            postalCode = response.postalCode,
            medicalExpDate = response.medicalExpDate.toString(),
            medicalStatus = response.medicalStatus
        )
        athleteDAO.insertAthlete(response.toEntity())
        return response
    }
}