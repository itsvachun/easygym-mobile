package com.easygym.data.remote.model.athlete

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AthleteListResponse(
    @SerialName("content") val content: List<AthleteResponse>,
    @SerialName("page") val page: Int,
    @SerialName("size") val size: Int,
    @SerialName("totalElements") val totalElements: Int,
    @SerialName("totalPages") val totalPages: Int,
    @SerialName("last") val last: Boolean,
)