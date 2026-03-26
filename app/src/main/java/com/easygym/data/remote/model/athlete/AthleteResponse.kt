package com.easygym.data.remote.model.athlete

import com.easygym.data.local.entity.AthleteEntity
import kotlinx.datetime.LocalDate
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
    @SerialName("birthDate") val birthDate: LocalDate,
    @SerialName("taxCode") val taxCode: String,
    @SerialName("address") val address: String,
    @SerialName("city") val city: String,
    @SerialName("postalCode") val postalCode: String,
    @SerialName("medicalExpDate") val medicalExpDate: LocalDate,
    @SerialName("medicalStatus") val medicalStatus: String,
) {
    fun toEntity(): AthleteEntity = AthleteEntity(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        phone = this.phone,
        enabled = this.enabled,
        birthDate = this.birthDate.toString(),
        taxCode = this.taxCode,
        address = this.address,
        city = this.city,
        postalCode = this.postalCode,
        medicalExpDate = this.medicalExpDate.toString(),
        medicalStatus = this.medicalStatus
    )
}
