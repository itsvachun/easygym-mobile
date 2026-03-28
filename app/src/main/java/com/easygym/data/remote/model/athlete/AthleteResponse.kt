package com.easygym.data.remote.model.athlete

import com.easygym.data.local.entity.AthleteEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AthleteResponse(
    @SerialName("id") val id: String,
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String,
    @SerialName("email") val email: String?,
    @SerialName("phone") val phone: String,
    @SerialName("enabled") val enabled: Boolean,
    @SerialName("birthDate") val birthDate: String,
    @SerialName("taxCode") val taxCode: String,
    @SerialName("address") val address: String,
    @SerialName("city") val city: String,
    @SerialName("postalCode") val postalCode: String,
    @SerialName("medicalExpDate") val medicalExpDate: String,
    @SerialName("medicalStatus") val medicalStatus: String,
) {
    fun toEntity(): AthleteEntity = AthleteEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
        enabled = enabled,
        birthDate = birthDate,
        taxCode = taxCode,
        address = address,
        city = city,
        postalCode = postalCode,
        medicalExpDate = medicalExpDate,
        medicalStatus = medicalStatus
    )
}
