package com.easygym.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easygym.domain.model.Athlete
import com.easygym.utils.enums.MedicalStatus
import java.time.LocalDate

@Entity(tableName = "athlete")
data class AthleteEntity(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String?,
    val phone: String,
    val enabled: Boolean,
    val birthDate: String,
    val taxCode: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val medicalExpDate: String,
    val medicalStatus: String,
) {
    fun toDomain(): Athlete = Athlete(
        firstName = firstName,
        lastName = lastName,
        email = email ?: "Email non disponibile",
        phone = phone,
        enabled = enabled,
        birthDate = LocalDate.parse(birthDate),
        taxCode = taxCode,
        address = address,
        city = city,
        postalCode = postalCode,
        medicalExpDate = LocalDate.parse(medicalExpDate),
        medicalStatus = MedicalStatus.valueOf(medicalStatus)
    )
}