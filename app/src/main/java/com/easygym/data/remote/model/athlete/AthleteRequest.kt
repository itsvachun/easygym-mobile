package com.easygym.data.remote.model.athlete

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AthleteRequest(
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String,
    @SerialName("birthDate") val birthDate: String,
    @SerialName("taxCode") val taxCode: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("phone") val phone: String,
    @SerialName("address") val address: String,
    @SerialName("city") val city: String,
    @SerialName("postalCode") val postalCode: String,
    @SerialName("medicalExpDate") val medicalExpDate: String,
    @SerialName("groupId") val groupId: String,
    @SerialName("notes") val notes: String,
)