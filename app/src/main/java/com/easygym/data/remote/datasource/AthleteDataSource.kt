package com.easygym.data.remote.datasource

import com.easygym.data.remote.model.athlete.AthleteRequest
import com.easygym.data.remote.model.athlete.AthleteResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AthleteDataSource {

    @POST("athletes")
    suspend fun postAthlete(@Body athleteRequest: AthleteRequest): AthleteResponse
}