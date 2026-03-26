package com.easygym.data.remote.datasource

import com.easygym.data.remote.model.group.GroupResponse
import retrofit2.http.GET

interface GroupDataSource {

    @GET("groups")
    suspend fun getAll(): List<GroupResponse>


}