package com.easygym.data.remote.datasource

import com.easygym.data.remote.model.athlete.AthleteListResponse
import com.easygym.data.remote.model.athlete.AthleteRequest
import com.easygym.data.remote.model.athlete.AthleteResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AthleteDataSource {
    @GET("athletes")
    suspend fun fetch(
        @Query("search") search: String,
        @Query("page") page: Int
    ): AthleteListResponse

    @POST("athletes")
    suspend fun postAthlete(@Body athleteRequest: AthleteRequest): AthleteResponse
}